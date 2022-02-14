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
        // 当frame==null, 代表返回的是半包消息, 直接返回, 继续由io线程读取后续的码流
        if (frame == null) {
            System.out.println("skip decode");
            return null;
        }
        System.out.println("start decode");
        // 注意: 这里需要用frame.readInt等来进行读取反序列化, 而不能直接使用in.readInt, 否则会导致读取错误!!
        NettyMessage nettyMessage = new NettyMessage();
        Header h = new Header();
        h.setCrcCode(frame.readInt());
        h.setLength(frame.readInt());
        h.setSessionId(frame.readLong());
        h.setType(frame.readByte());
        h.setPriority(frame.readByte());

        // 读取attachment的size
        int attachmentSize = frame.readInt();
        if (attachmentSize > 0) {
            Map<String, Object> attachment = Maps.newHashMapWithExpectedSize(attachmentSize);
            // 逐个把attachment反序列化出来
            for (int i = 0; i < attachmentSize; i++) {
                // 反序列化key
                int keyLength = frame.readInt();
                byte[] keyBytes = new byte[keyLength];
                frame.readBytes(keyBytes);
                String key = new String(keyBytes, StandardCharsets.UTF_8);

                // 反序列化value
                Object value = marshallingDecoder.decode(frame);
                attachment.put(key, value);
            }
            h.setAttachment(attachment);
        }
        nettyMessage.setHeader(h);

        // 反序列化body
        if (frame.readableBytes() > 4) {
            nettyMessage.setBody(marshallingDecoder.decode(frame));
        }
        return nettyMessage;
    }
}
