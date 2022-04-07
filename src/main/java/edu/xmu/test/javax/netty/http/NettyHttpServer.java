package edu.xmu.test.javax.netty.http;

import com.google.common.collect.Maps;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

import java.util.Map;

import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;

/**
 * 使用Netty实现的HTTP SERVER, 主要来测试 "Transfer-Encoding: chunked" 行为, 从而验证k8s中watch机制的长连接实现.
 * 1. 先参照<a href="https://www.baeldung.com/java-netty-http-server">HTTP Server with Netty</a> 实现一个简单的
 * 2. 再实现 "Transfer-Encoding: chunked" 行为的
 *
 * @author davywalker
 */
public class NettyHttpServer {

    /**
     * 业务线程池
     */

    public void bind(int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);

        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new HttpServerInitializer());
        ChannelFuture f = null;
        try {
            f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (
                InterruptedException e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            //workerGroup.shutdownGracefully();
        }
        //  为啥socketChannel会有parentChannel的概念? 父子channel的关系是啥?
        // AbstractChannelHandlerContext 组成了链表. 是有prevContext, nextContext的
    }

    public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel channel) throws Exception {
            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addLast(new HttpServerCodec());// http 编解码
            pipeline.addLast("httpAggregator", new HttpObjectAggregator(512 * 1024)); // http 消息聚合器                                                                     512*1024为接收的最大contentlength
            pipeline.addLast(new ChunkedWriteHandler());
            pipeline.addLast(new HttpRequestHandler());// 请求处理器

        }
    }

    public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
            //100 Continue
            if (is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(
                        HttpVersion.HTTP_1_1,
                        HttpResponseStatus.CONTINUE));
            }
            // 获取请求的uri
            String uri = req.getUri();
            Map<String, String> resMap = Maps.newHashMap();
            resMap.put("method", req.getMethod().name());
            resMap.put("uri", uri);
            String msg = "<html><head><title>test</title></head><body>你请求uri为：" + uri + "</body></html>";
            // 创建http响应
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
            // 设置头信息
            response.headers().set("content-type", "text/html; charset=UTF-8");
            // 将html write到客户端
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }


    public static void main(String[] args) {
        int port = 8888;
        new NettyHttpServer().bind(port);
    }
}
