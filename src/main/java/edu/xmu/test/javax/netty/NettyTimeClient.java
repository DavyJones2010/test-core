package edu.xmu.test.javax.netty;

import java.util.Calendar;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * Created by davywalker on 17/5/7.
 */
public class NettyTimeClient {

    EventExecutorGroup eventExecutorGroup = new DefaultEventExecutorGroup(16,
        new DefaultThreadFactory("MyNettyClientThread"));

    public void connect(int port, String host) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(
            new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ByteBuf byteBuf = Unpooled.copiedBuffer(",".getBytes());
                    ch.pipeline()
                        .addLast(new DelimiterBasedFrameDecoder(1024, byteBuf))
                        //.addLast(new LineBasedFrameDecoder(1024))
                        .addLast(new StringDecoder())
                        .addLast(new TimeClientReader())
                        .addLast(new TimeClientWriter());
                }
            });
        ChannelFuture f = null;
        try {
            f = b.connect(host, port).sync();
            //f.channel().closeFuture().sync();
            Thread.sleep(10000L);
            f.channel().close();
        } catch (InterruptedException e) {

            e.printStackTrace();
            group.shutdownGracefully();
        }
    }

    private class TimeClientReader extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String body = (String)msg;
            System.out.println("Now is " + body + " " + Calendar.getInstance().getTime());
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            System.out.println("Client channelReadComplete");
            super.channelReadComplete(ctx);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("exception caught, e=" + cause.getMessage());
            ctx.close();
        }
    }

    private class TimeClientWriter extends ChannelOutboundHandlerAdapter {

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            System.out.println("start write");
            super.write(ctx, msg, promise);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("exception caught, e=" + cause.getMessage());
            ctx.close();
        }
    }

    public static void main(String[] args) {
        int port = 8888;
        new NettyTimeClient().connect(port, "127.0.0.1");
    }
}
