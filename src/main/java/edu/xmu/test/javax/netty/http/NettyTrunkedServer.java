package edu.xmu.test.javax.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 使用Netty实现的FileSERVER, 主要来测试Netty输出大文件的时候,防止内存撑爆.
 * 参照
 * <a href="https://stackoverflow.com/questions/47794599/how-to-chunk-listobject-in-netty">how-to-chunk-listobject-in-netty</a>
 * 更完善的参照:
 * <a href="https://github.com/netty/netty/blob/79a7c157a3e122872321844b73c4881e412dba77/example/src/main/java/io/netty/example/http/file/HttpStaticFileServerHandler.java">Netty官方HttpStaticFileServerHandler样例</a>
 *
 * @author davywalker
 */
public class NettyTrunkedServer {
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
        } catch (InterruptedException e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();//
            workerGroup.shutdownGracefully();
        }
    }

    public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel channel) throws Exception {
            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new HttpObjectAggregator(65536));
            pipeline.addLast(new ChunkedWriteHandler());
            pipeline.addLast(new FileRequestHandler());//请求处理器
        }
    }

    public class FileRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) {
            ctx.flush();
        }

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req)
                throws Exception {
            System.out.println("channelRead0 invoked");
            String fileName = "/Users/davywalker/Downloads/295744.jpg";

            RandomAccessFile raf;
            File file = new File(fileName);
            try {
                raf = new RandomAccessFile(file, "r");
            } catch (FileNotFoundException ignore) {
//                sendError(ctx, NOT_FOUND);
                System.out.println("error");
                return;
            }
            long fileLength = raf.length();

            HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
//            HttpUtil.setContentLength(response, fileLength);
//            response.headers().set("Content-length", fileLength);
            setContentTypeHeader(response, file);
            // Write the initial line and the header.
            ctx.write(response);

//            byte[] imageInByte;
//            BufferedImage originalImage = ImageIO.read(new File(fileName));//convert BufferedImage to byte array
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(originalImage, "png", baos);
//            baos.flush();
//            imageInByte = baos.toByteArray();
//            baos.close();
//            msg.clear();
//            msg.add(0, "String");//add the String into List
//            msg.add(0, imageInByte); //add the bytes of images into list
            // 分批输出大文件
            ChannelFuture sendFileFuture = ctx.writeAndFlush(new HttpChunkedInput(new ChunkedFile(raf, 0, fileLength, 16)), ctx.newProgressivePromise());

            sendFileFuture.addListener(new ChannelProgressiveFutureListener() {
                @Override
                public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
                    if (total < 0) { // total unknown
                        System.out.println(future.channel() + " Transfer progress: " + progress);
                    } else {
                        System.out.println(future.channel() + " Transfer progress: " + progress + " / " + total);
                    }
                }

                @Override
                public void operationComplete(ChannelProgressiveFuture future) {
                    System.err.println(future.channel() + " Transfer complete.");
                }
            });
//            sendFileFuture.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private static void setContentTypeHeader(HttpResponse response, File file) {
        MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        response.headers().set("content-type", mimeTypesMap.getContentType(file.getPath()));
    }

    public static void main(String[] args) {
        int port = 8880;
        new NettyTrunkedServer().bind(port);
    }
}