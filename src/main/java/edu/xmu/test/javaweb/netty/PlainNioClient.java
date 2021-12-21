package edu.xmu.test.javaweb.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlainNioClient {
    SocketChannel sc;
    Selector selector;
    ByteBuffer dst = ByteBuffer.allocate(1024);
    SendThread sd = new SendThread();

    public PlainNioClient(String remoteAddr, int port) {
        try {
            selector = Selector.open();
            sc = SocketChannel.open();
            sc.configureBlocking(false);
            //sc.register(selector, SelectionKey.OP_CONNECT);
            //selector.select();
            boolean connect = sc.connect(new InetSocketAddress(remoteAddr, port));
            System.out.println("connected to server. connect: " + connect + " remote: " + sc.getRemoteAddress());
            if (!connect) {
                // 如果connect=false, 则代表连接尚未建立OK
                sc.register(selector, SelectionKey.OP_CONNECT);
            } else {
                // 如果connect=true, 则代表瞬间完成了连接??
                sc.register(selector, SelectionKey.OP_WRITE);
            }

            //Set<SelectionKey> selectedKeys = selector.selectedKeys();
            //Iterator<SelectionKey> iterator = selectedKeys.iterator();
            //while (iterator.hasNext()) {
            //    SelectionKedy next = iterator.next();
            //    boolean connectable = next.isConnectable();
            //    if (connectable) {
            //        System.out.println("connected to " + remoteAddr + ":" + port);
            //        sc.register(selector, SelectionKey.OP_WRITE);
            //    }
            //    iterator.remove();
            //}
            sd.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void send(String msg) {
        sd.send(msg);
    }

    class SendThread extends Thread {
        AtomicBoolean running = new AtomicBoolean(false);
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

        public void send(String msg) {
            queue.add(msg);
        }

        public void start() {
            running.set(true);
            super.start();
        }

        public void close() {
            running.set(false);
        }

        @Override
        public void run() {
            while (running.get()) {
                try {
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey k = iterator.next();
                        iterator.remove();
                        SocketChannel sc = ((SocketChannel)k.channel());
                        if (k.isConnectable()) {
                            if (sc.finishConnect()) {
                                System.out.println("connected to server. " + sc.getRemoteAddress().toString());
                                sc.register(selector, SelectionKey.OP_WRITE);
                            }
                        } else if (k.isReadable()) {
                            int read = sc.read(dst);
                            dst.flip();
                            String s = StandardCharsets.UTF_8.decode(dst).toString();
                            System.out.println("read from server: " + s);
                            dst.clear();
                            sc.register(selector, SelectionKey.OP_WRITE);
                        } else if (k.isWritable()) {
                            String msg = queue.take();
                            sc.write(ByteBuffer.wrap(msg.getBytes()));
                            System.out.println("write to server: " + msg);
                            sc.register(selector, SelectionKey.OP_READ);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    void close() {
        try {
            System.out.println("close connect");
            sd.close();
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PlainNioClient client = new PlainNioClient("127.0.0.1", 10800);
        client.send("hello");
        client.send("world");
        client.send("close");
        Thread.sleep(10000L);
        client.close();
    }
}
