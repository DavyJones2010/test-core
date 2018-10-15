package edu.xmu.test.javax.netty;

import java.util.Date;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by davywalker on 17/5/7.
 */
public class NettyTimeServer {

    /**
     * 业务线程池
     */
    EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(16,
        new DefaultThreadFactory("MyNettyThread"));

    public void bind(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); // 这个EventLoop用来监听OP_ACCEPT事件
        EventLoopGroup workerGroup = new NioEventLoopGroup(); // 这个EventLoopGroup用来监听OP_READ, OP_WRITE事件
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.SO_BACKLOG, 1024)
            .handler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    System.out.println("BossChannelHandler.initChannel invoked");
                }
            }).childHandler(new ChildChannelHandler());
        ChannelFuture f = null;
        try {
            f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            System.out.println("ChildChannelHandler.initChannel invoked");
            ByteBuf byteBuf = Unpooled.copiedBuffer(",".getBytes());
            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, byteBuf))
                //.addLast(new LineBasedFrameDecoder(1024))
                .addLast(new StringDecoder())
                .addLast(new MessageToMessageDecoder() {
                             @Override
                             protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
                                 System.out.println(
                                     "MessageToMessageDecoder Current thread is: " + Thread.currentThread().getName());
                                 out.add(msg);
                             }
                         }
                )
                .addLast(new TimeServerHandler());
        }
    }

    private class TimeServerHandler extends ChannelHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String body = (String)msg;
            System.out.println("The time server receive order: " + body);
            System.out.println("TimeServerHandler Current thread is: " + Thread.currentThread().getName());
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
            currentTime = currentTime + ',';
            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.channel().writeAndFlush(resp);
            //ctx.writeAndFlush(resp);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Server channelReadComplete");
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            ctx.close();
        }
    }

    public static void main(String[] args) {
        int port = 8888;
        new NettyTimeServer().bind(port);
    }
}
