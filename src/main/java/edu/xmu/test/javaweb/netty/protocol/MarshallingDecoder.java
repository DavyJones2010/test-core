package edu.xmu.test.javaweb.netty.protocol;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public class MarshallingDecoder {
    /**
     * 4个字节: 标识内容长度
     * 不定长字节: 内容
     *
     * @param in
     */
    public Object decode(ByteBuf in) {
        int length = in.readInt();
        if(0 == length) {
            // 长度是0, 则直接跳出.
            System.out.println("zero length");
            return null;
        }
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        String bodyStr = new String(bytes, StandardCharsets.UTF_8);
        // JSON反序列化
        return JSON.parse(bodyStr);
    }
}
