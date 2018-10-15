package edu.xmu.test.javax.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

/**
 * Created by davywalker on 17/5/7.
 */
public class NettyBaseTest {
    @Test
    public void byteBufTest() throws Exception {
        byte[] bytes = "HELLO ".getBytes("GBK");

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(bytes);
        bytes = "WORLD".getBytes("UTF-8");
        byteBuf.writeBytes(bytes);

        byte[] bytes1 = new byte[5];
        byteBuf.readBytes(bytes1);
        System.out.println(new String(bytes1, "UTF-8"));
        System.out.println(byteBuf.readableBytes());
        while (byteBuf.readableBytes() > 0) {
            byte[] bytes2 = new byte[1];
            bytes2[0] = byteBuf.readByte();
            System.out.println(byteBuf.writableBytes());
            System.out.println(new String(bytes2, "UTF-8"));
        }
        byteBuf.discardReadBytes();
        System.out.println(byteBuf.writableBytes());
    }
}
