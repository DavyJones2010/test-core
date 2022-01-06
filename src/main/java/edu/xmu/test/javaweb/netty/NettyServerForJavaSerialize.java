package edu.xmu.test.javaweb.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Netty处理Java序列化
 */
public class NettyServerForJavaSerialize {
    public void bind(int port) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();

            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers
                                    .weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                            socketChannel.pipeline().addLast(new ObjectEncoder());
                            socketChannel.pipeline().addLast(new SubReqServerHandler());
                        }
                    });

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public class SubReqServerHandler extends ChannelHandlerAdapter {
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("server received connection: " + ctx.channel().toString());
        }

        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            SubscribeReq req = (SubscribeReq) msg;
            System.out.println("server read: " + req.toString());
            ctx.writeAndFlush(resp(req.subReqId));
        }

        private SubscribeResp resp(int subReqId) {
            SubscribeResp resp = new SubscribeResp();
            resp.subReqId = subReqId;
            resp.respCode = 0;
            resp.desc = "Hello from server";
            return resp;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int port = 8888;
        new NettyServerForJavaSerialize().bind(port);
    }
}
