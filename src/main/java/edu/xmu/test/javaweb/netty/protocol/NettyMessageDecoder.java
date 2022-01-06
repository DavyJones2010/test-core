package edu.xmu.test.javaweb.netty.protocol;

import com.google.common.collect.Maps;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
    MarshallingDecoder marshallingDecoder;

    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        // 最关键的是这三个参数:
        // maxFrameLength: 每个包的最大字节长度, 由于标识包大小的length为int类型, 因此包最大字节数为 2^32
        // lengthFieldOffset: 每个包里, 标识包大小(length)字段的偏移量, 本协议中由于length字段前边有crc(int类型), 因此为4个字节
        // lengthFieldLength: 标识包大小(length)字段自身占用的字节数, 本协议中为int类型, 即占用4个字节
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
        marshallingDecoder = new MarshallingDecoder();
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }
        NettyMessage nettyMessage = new NettyMessage();
        NettyMessage.Header h = new NettyMessage.Header();
        h.setCrcCode(in.readInt());
        h.setLength(in.readInt());
        h.setSessionId(in.readLong());
        h.setType(in.readByte());
        h.setPriority(in.readByte());

        // 读取attachment的size
        int attachmentSize = in.readInt();
        if (attachmentSize > 0) {
            Map<String, Object> attachment = Maps.newHashMapWithExpectedSize(attachmentSize);
            // 逐个把attachment反序列化出来
            for (int i = 0; i < attachmentSize; i++) {
                // 反序列化key
                int keyLength = in.readInt();
                byte[] keyBytes = new byte[keyLength];
                in.readBytes(keyBytes);
                String key = new String(keyBytes, StandardCharsets.UTF_8);

                // 反序列化value
                Object value = marshallingDecoder.decode(in);
                attachment.put(key, value);
            }
            h.setAttachment(attachment);
        }
        nettyMessage.setHeader(h);
        
        // 反序列化body
        if (in.readableBytes() > 4) {
            nettyMessage.setBody(marshallingDecoder.decode(in));
        }
        return nettyMessage;
    }
}
