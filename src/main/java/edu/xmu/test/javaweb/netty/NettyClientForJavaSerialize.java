package edu.xmu.test.javaweb.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * 一个简单的NettyClient, 用于采用原生的Java序列化方式向服务端发送Java对象信息;
 * 通过获取到服务端返回的Java对象后, 通过原生的Java反序列化方式反序列化出来
 */
public class NettyClientForJavaSerialize {
    public void connect(int port, String host) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader()))
                            ).addLast(
                                    new ObjectEncoder()
                            ).addLast(new SubReqClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyClientForJavaSerialize().connect(8888, "127.0.0.1");
    }

    class SubReqClientHandler extends ChannelHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            for (int i = 0; i < 10; i++) {
                // httodo: 这里如果每次write都flush一下, 会怎样?
                ctx.write(subReq(i));
            }
            ctx.flush();
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("client receive from server: " + msg.toString());
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            // httodo: 这里为什么需要在readComplete时flush一下?
            ctx.flush();
        }

        private SubscribeReq subReq(int i) {
            SubscribeReq r = new SubscribeReq();
            r.subReqId = i;
            r.userName = "hanting-" + i;
            r.address = "hangzhou-" + i;
            return r;
        }
    }
}
