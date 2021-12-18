package edu.xmu.test.javaweb.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class PlainNioEchoServer {
    public void serve(int port) throws IOException {
        System.out.println("Listening for connections on port: " + port);
        ServerSocketChannel channel = ServerSocketChannel.open();
        ServerSocket serverSocket = channel.socket();
        serverSocket.bind(new InetSocketAddress(port));

        channel.configureBlocking(false);
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer dst = ByteBuffer.allocate(256);
        while (true) {
            selector.select();

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel)key.channel();
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    System.out.println("connected clientAddr: " + client.getRemoteAddress().toString() + " clientId: "
                        + client.hashCode());
                    client.register(selector, SelectionKey.OP_READ);
                    //channel.register(selector, SelectionKey.OP_READ);
                    //key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_ACCEPT);
                } else if (key.isReadable()) {
                    SocketChannel clientChannel = (SocketChannel)key.channel();
                    int read = clientChannel.read(dst);
                    if(-1 == read) {
                        clientChannel.close();
                        System.out.println("clientId: " + clientChannel.hashCode() + " closed");
                    } else {
                        byte[] array = dst.slice().array();
                        String s = new String(array);
                        //dst.position()
                        //String s = new String(dst.array());
                        //String s = StandardCharsets.ISO_8859_1.decode(dst).toString();
                        System.out.println("clientId: " + clientChannel.hashCode() + " says: " + s);
                    }
                    dst.clear();
                }
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new PlainNioEchoServer().serve(10800);

    }
}
