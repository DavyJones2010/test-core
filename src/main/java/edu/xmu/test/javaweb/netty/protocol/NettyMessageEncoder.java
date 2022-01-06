package edu.xmu.test.javaweb.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 原文源代码地址:
 * https://gitcode.net/mirrors/wuyinxian124/nettybook2/-/blob/master/src/com/phei/netty/protocol/netty/codec/NettyMessageEncoder.java
 *
 * 消息编码器, 将NettyMessage序列化成byte[];
 * 针对attachment, 编码规则:
 * - 如果attachment长度为0, 表示没有可选附件, 则将长度编码设置为0, ByteBuffer.putInt(0)
 * - 如果attachment长度>0, 则
 * -- 首先编码附件个数: ByteBuffer.putInt(attachment.size());
 * -- 再将key用ByteBuffer.writeString(key); 将value用JBoss Marshaller(用JSON吧) ByteBuffer.writeBinary(marshaller.writeObject(value));
 */
public class NettyMessageEncoder extends MessageToByteEncoder<NettyMessage> {
    MarshallingEncoder marshallingEncoder;

    public NettyMessageEncoder() {
        this.marshallingEncoder = new MarshallingEncoder();
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf sendBuf) throws Exception {
        // 消息头
        sendBuf.writeInt(msg.getHeader().getCrcCode());
        // 在最后将这个length重写覆盖了
        sendBuf.writeInt(msg.getHeader().getLength());
        sendBuf.writeLong(msg.getHeader().getSessionId());
        sendBuf.writeByte(msg.getHeader().getType());
        sendBuf.writeByte(msg.getHeader().getPriority());
        sendBuf.writeByte(msg.getHeader().getAttachment().size());
        // 消息附件
        for (Map.Entry<String, Object> entry : msg.getHeader().getAttachment().entrySet()) {
            // 序列化key
            String key = entry.getKey();
            sendBuf.writeInt(key.length());
            sendBuf.writeBytes(key.getBytes(StandardCharsets.UTF_8));

            // 序列化value
            Object value = entry.getValue();
            marshallingEncoder.encode(value, sendBuf);
        }

        // 消息体
        if (msg.getBody() != null) {
            marshallingEncoder.encode(msg.getBody(), sendBuf);
        } else {
            // httodo: 为啥最后要写个int?
            sendBuf.writeInt(0);
        }
        // httodo: 在这里将length重写覆盖了. 为啥要减去8个字节?
        sendBuf.setInt(4, sendBuf.readableBytes() - 8);
    }
}
