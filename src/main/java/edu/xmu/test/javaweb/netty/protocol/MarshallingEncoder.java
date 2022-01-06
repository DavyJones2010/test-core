package edu.xmu.test.javaweb.netty.protocol;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class MarshallingEncoder {
    /**
     * 4个字节: 标识内容长度
     * 不定长字节: 内容
     *
     * @param msg
     * @param out
     */
    public void encode(Object msg, ByteBuf out) {
        // 采用JSON序列化
        byte[] bytes = JSON.toJSONString(msg).getBytes(StandardCharsets.UTF_8);
        // length是内容的大小, 不包括"length"本身的4个字节长度
        int length = bytes.length;
        out.writeInt(length);
        out.writeBytes(bytes);
    }
}
