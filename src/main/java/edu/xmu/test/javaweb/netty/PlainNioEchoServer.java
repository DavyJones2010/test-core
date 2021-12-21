package edu.xmu.test.javaweb.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
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

        ByteBuffer dst = ByteBuffer.allocate(1024);
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
                    // 如果 read == -1; 则代表EOS(EndOfStream), 即对端已经关闭掉了写出侧的socket, 此时服务端需要至少deregister(OP_READ);
                    // 如果不deregister/closeChannel, 则会一直readable==true, 告诉服务端EOS了(死循环).
                    // End of stream on a socket means the peer has closed the connection.
                    // When you get EOS, you must either close the channel or at least deregister interest in OP_READ
                    // If bytesRead < 0 you should close the channel or at least deregister OP_READ. Otherwise you
                    // will keep getting OP_READ over and over again to tell you about the EOS.

                    //                    if (-1 == read) {
                    //                        clientChannel.close();
                    //                        System.out.println("clientId: " + clientChannel.hashCode() + " closed");
                    //                    } else {
                    // 这里没有解决拆包问题(如果请求过来的数据包太大, 没有同时到达网卡缓冲区)
                    dst.flip();
                    String s = StandardCharsets.UTF_8.decode(dst).toString();
                    System.out.println("clientId: " + clientChannel.hashCode() + " says: " + s);
                    dst.flip();
                    clientChannel.register(selector, SelectionKey.OP_WRITE);
                    //                    clientChannel.register(selector, SelectionKey.OP_READ | SelectionKey
                    //                    .OP_WRITE);
                    //                    此时由于业务上对于缓冲区读取已经读完了, 但实际底层操作系统缓冲区中仍然还是原来的数据(并不知道读完了), 所以selector.select()
                    //                    会始终返回可读.
                    //                    所以这里业务上已经读完了, 就不要再注册OP_READ了.
                    //                    TODO: 但有什么办法告知操作系统已经读完了, 可以把网卡度缓冲区中数据清理掉了呢?? 这样效率是不是更高一些?
                    //                    TODO: 如果不清理操作系统读缓冲区, 会不会导致后边缓冲区溢出?
                    //                    }
                } else if (key.isWritable()) {
                    SocketChannel clientChannel = (SocketChannel)key.channel();
                    clientChannel.write(dst);
                    dst.clear();
                    clientChannel.register(selector, SelectionKey.OP_READ);
                    //clientChannel.close();
                }
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new PlainNioEchoServer().serve(10800);
        // hantingtodo: 服务端如何得知某个客户端主动关闭了连接?
    }
}
