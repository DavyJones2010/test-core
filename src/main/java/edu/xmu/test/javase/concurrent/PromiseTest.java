package edu.xmu.test.javase.concurrent;

import java.util.Calendar;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;

/**
 * 测试netty中Promise的使用
 */
public class PromiseTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        DefaultEventExecutor defaultEventExecutor = new DefaultEventExecutor();
        Promise<Object> objectPromise = defaultEventExecutor.newPromise();
        // 由Executor创建出来的Promse, 回调方法会在Executor的线程中执行.
        objectPromise.addListener(new GenericFutureListener<Future<Object>>() {
            @Override
            public void operationComplete(Future future) throws Exception {
                System.out.println(
                    Thread.currentThread().getName() + " inEventLoop: " + defaultEventExecutor.inEventLoop());
                if (future.isSuccess()) {
                    System.out.println(Thread.currentThread().getName() + " Success: " + future.get());
                } else {
                    System.out.println(Thread.currentThread().getName() + " Failed: " + future.cause());
                }
            }
        });

        Future<String> submit = defaultEventExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println(
                    Thread.currentThread().getName() + " inEventLoop: " + defaultEventExecutor.inEventLoop());
                Thread.sleep(1000L);
                String res = Calendar.getInstance().getTime().toString();
                //objectPromise.setSuccess(res);
                objectPromise.setFailure(new Throwable("system error"));
                return res;
            }
        });

        // submit回调方法会在创建该submit的Executor的线程中执行. 如何在主线程中执行回调?
        //submit.addListener(new GenericFutureListener() {
        //    @Override
        //    public void operationComplete(Future future) throws Exception {
        //        System.out.println(
        //            Thread.currentThread().getName() + " inEventLoop: " + defaultEventExecutor.inEventLoop());
        //        if (future.isSuccess()) {
        //            System.out.println(Thread.currentThread().getName() + " Success: " + future.get());
        //        } else {
        //            System.out.println(Thread.currentThread().getName() + " Failed: " + future.cause());
        //        }
        //    }
        //});

        DefaultEventExecutor defaultEventExecutor2 = new DefaultEventExecutor();
        Promise<Object> objectPromise1 = defaultEventExecutor2.newPromise();
        defaultEventExecutor2.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Future<String> submit1 = defaultEventExecutor.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        System.out.println("start " +
                            Thread.currentThread().getName() + " inEventLoop1: " + defaultEventExecutor.inEventLoop());
                        System.out.println("start " +
                            Thread.currentThread().getName() + " inEventLoop2: " + defaultEventExecutor2.inEventLoop());
                        Thread.sleep(1000L);
                        objectPromise1.setSuccess("");
                        return null;
                    }
                });

                submit1.addListener(new GenericFutureListener() {
                    @Override
                    public void operationComplete(Future future) throws Exception {
                        System.out.println("finished " +
                            Thread.currentThread().getName() + " inEventLoop1: " + defaultEventExecutor.inEventLoop());
                        System.out.println("finished " +
                            Thread.currentThread().getName() + " inEventLoop2: " + defaultEventExecutor2.inEventLoop());
                    }
                });
                return null;
            }
        });
        objectPromise1.addListener(new GenericFutureListener() {
            @Override
            public void operationComplete(Future future) throws Exception {
                System.out.println("finished2 " +
                    Thread.currentThread().getName() + " inEventLoop1: " + defaultEventExecutor.inEventLoop());
                System.out.println("finished2 " +
                    Thread.currentThread().getName() + " inEventLoop2: " + defaultEventExecutor2.inEventLoop());
            }
        });
        //Promise<Object> objectPromise2 = defaultEventExecutor2.newPromise();
        //objectPromise2.addListener();
        //
        //while (!submit.isSuccess()) {
        //    System.out.println("Wait");
        //    Thread.sleep(100L);
        //}
        //
        //String s = submit.get();
        //System.out.println(s);
        //
        //defaultEventExecutor.shutdownGracefully();
        //Future<String> submit = defaultEventExecutor.submit(new Callable<String>() {
        //    @Override
        //    public String call() throws Exception {
        //        Thread.sleep(1000L);
        //        System.out.println(
        //            Thread.currentThread().getName() + " inEventLoop: " + defaultEventExecutor.inEventLoop());
        //        defaultEventExecutor.submit(new Runnable() {
        //            @Override
        //            public void run() {
        //                System.out.println(
        //                    Thread.currentThread().getName() + " inEventLoop: " + defaultEventExecutor.inEventLoop()
        //                        + " hello");
        //            }
        //        });
        //        return Calendar.getInstance().getTime().toString();
        //    }
        //});
        //while (!submit.isSuccess()) {
        //    System.out.println("Wait");
        //    System.out.println(
        //        Thread.currentThread().getName() + " inEventLoop: " + defaultEventExecutor.inEventLoop());
        //    Thread.sleep(100L);
        //}

        //String s = submit.get();
        //System.out.println(s);
    }
}
