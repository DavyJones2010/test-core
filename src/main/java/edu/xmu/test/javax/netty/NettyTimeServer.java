package edu.xmu.test.javax.netty;

import java.util.Date;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
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
        // 这个EventLoop用来监听OP_ACCEPT事件
        // hantingfixme: 为啥需要单独的eventLoopGroup来监听ACCEPT?
        // 如果与workerGroup共用一个, 会有什么问题?
        // 在什么时候, bossGroup会设置多于1个线程? 线程池名称: nioEventLoopGroup-3-1
        // 样例: "nioEventLoopGroup-3-1" nioEventLoopGroup: 代表类型; 3: 代表全局netty的第3个线程池(不分类型); 1: 代表该线程池中的第一个线程
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        // 这个EventLoopGroup用来监听OP_READ, OP_WRITE事件
        // hantingfixme: 线程池里线程名称是啥?
        // 线程名: io.netty.util.concurrent.DefaultThreadFactory.DefaultThreadFactory(java.lang.String, boolean, int)
        // 样例: "nioEventLoopGroup-4-1" nioEventLoopGroup: 代表类型; 4: 代表全局netty的第4个线程池; 1: 代表该线程池中的第一个线程
        // 在各个filter/handler里, 执行业务操作, 也是在IO线程么? 会不会阻塞IO线程?
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        ServerBootstrap b = new ServerBootstrap();
        //b.group(bossGroup, workerGroup)
        b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.SO_BACKLOG, 1024)
            .handler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    // 这里会在server初始化完成, 开始监听端口号之后调用. 在bossGroup线程池里.
                    System.out.println(Thread.currentThread() + " BossChannelHandler.initChannel invoked");
                    // hantingfixme: 如何在bossGroup线程里, 获知到client的accept事件?
                }

                @Override
                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                    System.out.println(Thread.currentThread() + " BossChannelHandler.channelRead invoked");
                    super.channelRead(ctx, msg);
                }

                @Override
                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                    System.out.println(Thread.currentThread() + " BossChannelHandler.channelActive invoked");
                    super.channelActive(ctx);
                }
            }).childHandler(
                // 这里会在客户端已经建立成功后执行, 整个运行在workerGroup线程池里.
                new ChildChannelHandler()
            );
        ChannelFuture f = null;
        try {
            f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            //workerGroup.shutdownGracefully();
        }
        //  为啥socketChannel会有parentChannel的概念? 父子channel的关系是啥?
        // AbstractChannelHandlerContext 组成了链表. 是有prevContext, nextContext的
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            System.out.println(System.currentTimeMillis() + " " + Thread.currentThread()
                + " ChildChannelHandler.initChannel invoked client: " + socketChannel.remoteAddress().toString());
            //ByteBuf byteBuf = Unpooled.copiedBuffer(",".getBytes());
            // hantingtodo: socketChannel与pipeline关系是啥? 一对一么?
            socketChannel.pipeline()
                //.addLast(new DelimiterBasedFrameDecoder(1024, byteBuf))
                // hantingtodo: 如果实际单行长度超过maxLengh:
                //  A TooLongFrameException is thrown if the length of the frame exceeds this value.
                // 就不会走到这个decoder之后的handler里了. 例如StringDecoder, TimeServerHandler 等.
                // hantingfixme: 在哪个地方可以捕获到该异常?
                .addLast(new LineBasedFrameDecoder(2))
                .addLast(new StringDecoder())
                .addLast(new MessageToMessageDecoder() {
                             @Override
                             protected void decode(ChannelHandlerContext ctx, Object msg, List out) throws Exception {
                                 System.out.println(
                                     Thread.currentThread() + " MessageToMessageDecoder Current thread is: "
                                         + Thread.currentThread().getName());
                                 out.add(msg);
                             }
                         }
                ).addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        System.out.println("tmp read: " + msg);
                        super.channelRead(ctx, msg);
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        System.out.println("CC caught exception:");
                        cause.printStackTrace();
                    }
                })
                .addLast(new TimeServerHandler());
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("AA caught exception:");
            cause.printStackTrace();
            //super.exceptionCaught(ctx, cause);
        }
    }

    private class TimeServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            String body = (String)msg;
            System.out.println(Thread.currentThread() + " The time server receive order: " + body);
            System.out.println(
                Thread.currentThread() + " TimeServerHandler Current thread is: " + Thread.currentThread().getName());
            String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date().toString() : "BAD ORDER";
            currentTime = currentTime + ',';
            Thread.sleep(10000L);
            ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.channel().writeAndFlush(resp);
            //ctx.writeAndFlush(resp);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            // hantingfixme: 为啥这里会被执行2次? 为啥会在 channelRead调用之后才被调用?
            // 参照这个: https://blog.csdn.net/MarchRS/article/details/104312146
            // 但还是不合理, 每次客户端只发送1个字节, 但这个接口仍然被调用2次?
            System.out.println(Thread.currentThread() + " Server channelReadComplete");
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("BB caught exception:");
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) {
        int port = 8888;
        new NettyTimeServer().bind(port);
    }
}
