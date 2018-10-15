package edu.xmu.test.javase.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * Created by kunlun.ykl on 2018/9/17.
 */
public class CsvSortTest {
    @Test
    public void test() throws Exception {
        //BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/input.csv"), 4 * 1024);
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/input.csv"));
        //String line;
        long start = System.nanoTime();

        while (null != bufferedReader.readLine()) {
            //System.out.println(line);
        }
        long end = System.nanoTime();
        System.out.println(end - start);
    }

    @Test
    public void test2() throws Exception {
        BufferedInputStream inputStream = new BufferedInputStream(
            new FileInputStream("src/test/resources/input.csv"));
        int val;
        long start = System.nanoTime();

        while (-1 != (val = inputStream.read())) {
        }
        long end = System.nanoTime();
        System.out.println(end - start);
    }

    @Test
    public void test3() throws Exception {
        BufferedInputStream inputStream = new BufferedInputStream(
            new FileInputStream("src/test/resources/input.csv"));
        int var;
        long start = System.nanoTime();

        byte[] b = new byte[1024 * 64];
        //int offset = 0;
        while (-1 != (var = inputStream.read(b, 0, 1024 * 64))) {
        }
        long end = System.nanoTime();
        System.out.println(end - start);
    }

    @Test
    public void test4() throws Exception {
        FileInputStream inputStream = new FileInputStream("src/test/resources/input.csv");
        int var;
        //List<Data> datas = new ArrayList<Data>(3000000);
        List<Data> datas = Lists.newArrayList();

        long start = System.nanoTime();

        //Data[] datas = new Data[3000000];
        byte[] b = new byte[1024 * 64];
        //int offset = 0;
        char delimter = ',';
        char newline = '\n';

        byte[] bytes = new byte[33];
        int innerCursor = 0;

        int count = 0;

        while (-1 != (var = inputStream.read(b, 0, 1024 * 64))) {
            int startCopyPosition = 0;
            for (int i = 0; i < var; i++) {
                //bytes[innerCursor] = b[i];
                if (b[i] == newline) {
                    //datas.add(new Data(bytes, innerCursor));
                    System.arraycopy(b, startCopyPosition, bytes, 0, i - startCopyPosition);
                    //datas[count]=new Data(bytes, innerCursor);
                    datas.add(new Data(bytes, innerCursor));
                    //new Data(bytes, innerCursor);
                    //bytes = new byte[33];
                    startCopyPosition = i + 1;
                    count++;
                } else {
                    innerCursor++;
                }
            }
        }
        long end = System.nanoTime();
        System.out.println(end - start);
    }

    class Data {
        byte[] bytes;
        int length;

        int index;
        double postfix;
        String value;

        public Data(byte[] bytes, int length) {
            this.bytes = bytes;
            this.length = length;
        }

        public Data(int index, double postfix, String value) {
            this.index = index;
            this.postfix = postfix;
            this.value = value;
        }

    }

    @Test
    public void test5() throws Exception {
        File file = new File("src/test/resources/input.csv");
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel channel = inputStream.getChannel();
        MappedByteBuffer buff = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
        long start = System.nanoTime();
        byte[] b = new byte[1024 * 64];
        int len = (int)file.length();

        for (int offset = 0; offset < len; offset += 1024 * 64) {
            if (len - offset > 1024 * 64) {
                buff.get(b);
            } else {
                buff.get(new byte[len - offset]);
            }
        }
        long end = System.nanoTime();
        System.out.println(end - start);
    }
}
