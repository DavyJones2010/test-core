package edu.xmu.test.javase.misc;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;
import java.util.UUID;

/**
 * Created by davywalker on 16/10/10.
 */
public class ConcurrentUpdateError {
    static class Model {
        String name;
        int version;
        String value;

        public Model(String name) {
            this.name = name;
        }

        public Model(String name, int version) {
            this.name = name;
            this.version = version;
        }
    }

    static Map<String, Model> map = Maps.newConcurrentMap();

    static {
        map.put("davy", new Model("davy"));
    }

    static class Dao {
        public boolean update(String name) {
            Model newModel = map.get(name);
            int version = newModel.version;
            newModel.value = UUID.randomUUID().toString();
            try {
                Thread.sleep((long) (Math.random() * 1000L));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Model oldModel = map.get(name);
            if (oldModel.version == version) {
                newModel.version = version + 1;
                map.put(name, newModel);
                return true;
            } else {
                return false;
            }
        }
    }

    @Test
    public void concurrentUpdateError() throws Exception {
        final Dao dao = new Dao();
        final Dao dao2 = new Dao();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep((long) (Math.random() * 1000L));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    boolean update = dao.update("davy");
                    System.out.println(Thread.currentThread().toString() + " updateResult=" + update);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep((long) (Math.random() * 1000L));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    boolean update = dao2.update("davy");
                    System.out.println(Thread.currentThread().toString() + " updateResult=" + update);
                }
            }
        }).start();
        Thread.sleep(1200000L);
    }
}
