package edu.xmu.test.javaweb.netty.protocol;

import com.google.common.collect.Maps;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Map;

public class NettyPrivateProtocolClient {
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
                            socketChannel.pipeline()
//                                    .addLast(new NettyMessageDecoder(1024 * 1024, 4, 4))
                                    .addLast(new NettyMessageEncoder())
                                    .addLast(new DefaultClientHandler());
                        }
                    });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public class DefaultClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            for (int i = 0; i < 10; i++) {
                NettyMessage nettyMessage = new NettyMessage();
                Header h = new Header();
                h.setSessionId(1L);
                h.setType((byte) 1);
                h.setPriority((byte) 1);
                Map<String, Object> attachment = Maps.newHashMap();
                attachment.put("sequence", i);
                h.setAttachment(attachment);
                nettyMessage.setHeader(h);
                nettyMessage.setBody("hello world!");
                ctx.write(nettyMessage);
                System.out.println("client send " + nettyMessage);
            }
            ctx.flush();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new NettyPrivateProtocolClient().connect(8888, "localhost");
    }

}
