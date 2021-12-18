package edu.xmu.test.ql;

import com.google.common.collect.Lists;
import org.apache.commons.lang.math.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by davywalker on 17/1/23.
 */
public class ExpressRunnerTest {
    /*ExpressRunner runner;
    DefaultContext<String, Object> context;
    String text;

    @Before
    public void setUp() {
        runner = new ExpressRunner();
        context = new DefaultContext<String, Object>();
    }

    @Test
    public void baseTest() throws Exception {
        String text = "sum=0;for(i=0;i<10;i=i+1){sum=sum+i;} return sum;";
        Object r = runner.execute(text, context, null, true, true);
        System.out.println(r);
    }

    @Test
    public void baseTest2() throws Exception {
        runner = new ExpressRunner();
        text = "for(i=0;i<count;i=i+1) { sum = sum+i;} return sum;";
        context.put("count", 10);
        context.put("sum", 10);
        Object r = runner.execute(text, context, null, true, true);
        System.out.println(r);
    }

    @Test
    public void addOperatorWithAliasTest() throws Exception {
        runner.addOperatorWithAlias("如果", "if", null);
        text = "如果(i > 10){return true;}return false;";
        context.put("i", 11);
        Object r = runner.execute(text, context, null, true, true);
        System.out.println(r);
        context.put("i", 9);
        r = runner.execute(text, context, null, true, true);
        System.out.println(r);
    }

    *//**
     * 自定义操作符号: addOperatorWithAlias, addOperator, addFunction
     *
     * @throws Exception
     *//*
    @Test
    public void addOperatorTest() throws Exception {
        runner.addOperator("join", new JoinOperator());
        Object r = runner.execute("1 join 2 join 3", context, null, false, false);
        System.out.println(r);
    }

    *//**
     * 自定义操作符号: addFunction
     *
     * @throws Exception
     *//*
    @Test
    public void addFunctionTest() throws Exception {
        runner.addFunction("group", new GroupOperator("group"));
        Object r = runner.execute("group(1,2,3)", context, null, false, false);
        System.out.println(r);
    }

    @Test
    public void addFunctionOfServiceMethodTest() throws Exception {
        runner.addFunctionOfServiceMethod("打印", System.out, "println", new String[]{"String"}, null);
        String exp = "打印(\"hello\");return 1";
        Object execute = runner.execute(exp, context, null, false, false);
        System.out.println(execute);

        runner.addFunctionOfServiceMethod("contains", new BeanExample(), "anyContains",
                new Class[]{String.class, String.class}, null);
        exp = "if(contains(\"helloworld\",\"aeiou\")){打印(\"hello\");return 1;}else{return 0;}";
        execute = runner.execute(exp, context, null, false, false);
        System.out.println(execute);

        exp = "if(contains(\"helloworld\",\"n\")){打印(\"hello\");return 1;}else{打印(\"world\");return 0;}";
        execute = runner.execute(exp, context, null, false, false);
        System.out.println(execute);
    }

    *//**
     * 类的静态方法: addFunctionOfClassMethod
     *
     * @throws Exception
     *//*
    @Test
    public void addFunctionOfClassMethodTest() throws Exception {
    }

    *//**
     * 判定runner是否是线程安全的
     *
     * @throws Exception
     *//*
    @Test
    public void concurrentTest() throws Exception {
        String express = "if(i<10){return i} else{return i;}";
        List<Thread> threads = Lists.newArrayList();
        for (int i = 0; i <= 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        DefaultContext<String, Object> c = new DefaultContext<>();
                        c.put("i", RandomUtils.nextInt(20));
                        System.out.println("Thread " + Thread.currentThread().getId() + " put i " + c.get("i"));
                        Thread.sleep(RandomUtils.nextInt(1000 * 5));
                        Object execute = runner.execute(express, c, null, true, true);
                        Thread.sleep(RandomUtils.nextInt(1000 * 5));
                        System.out.println("Thread " + Thread.currentThread().getId() + " returns execute " + execute);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            threads.add(t);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        Thread.sleep(1000 * 5L);
    }

    public class JoinOperator extends Operator {
        public Object executeInner(Object[] list) throws Exception {
            Object opdata1 = list[0];
            Object opdata2 = list[1];
            if (opdata1 instanceof java.util.List) {
                ((java.util.List) opdata1).add(opdata2);
                return opdata1;
            } else {
                java.util.List result = new java.util.ArrayList();
                result.add(opdata1);
                result.add(opdata2);
                return result;
            }
        }
    }

    class GroupOperator extends Operator {
        public GroupOperator(String aName) {
            this.name = aName;
        }

        public Object executeInner(Object[] list) throws Exception {
            Object result = Integer.valueOf(0);
            for (int i = 0; i < list.length; i++) {
                result = OperatorOfNumber.add(result, list[i], false);//根据list[i]类型（string,number等）做加法
            }
            return result;
        }
    }
*/
}