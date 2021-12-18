package edu.xmu.test.javase.nio;

import org.junit.Test;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import static org.junit.Assert.assertEquals;

/**
 * 基础的bytebuffer常用操作测试
 */
public class ByteBufferTest {
    @Test
    public void baseParamTest() {
        // capacity, limit, position, mark
//        Capacity	容量，即可以容纳的最大数据量；在缓冲区创建时被设定并且不能改变
//        Limit	表示缓冲区的当前终点，不能对缓冲区超过极限的位置进行读写操作。且limit是可以修改的
//        Position	位置，下一个要被读或写的元素的索引，每次读写缓冲区数据时都会改变改值，为下次读写作准备
//        mark <= position <= limit <= capacity
        ByteBuffer allocate = ByteBuffer.allocate(128);
        assertEquals(128, allocate.capacity());
        assertEquals(128, allocate.limit());
        assertEquals(0, allocate.position());
        System.out.println("capacity: " + allocate.capacity()
                + " limit: " + allocate.limit()
                + " position: " + allocate.position()
        );
        allocate.put((byte) 'a');
        allocate.put((byte) 'b');
        allocate.put((byte) 'c');
        System.out.println("capacity: " + allocate.capacity()
                + " limit: " + allocate.limit()
                + " position: " + allocate.position()
        );
        assertEquals(128, allocate.capacity());
        assertEquals(128, allocate.limit());
        assertEquals(3, allocate.position());
    }

    @Test
    public void limitTest() {
        ByteBuffer allocate = ByteBuffer.allocate(128);
        allocate.limit(3);
        allocate.put((byte) 'a');
        allocate.put((byte) 'b');
        allocate.put((byte) 'c');
        try {
            allocate.put((byte) 'd');
        } catch (BufferOverflowException e) {
            e.printStackTrace();
        }
        allocate.limit(4);
        allocate.put((byte) 'd');
    }


    @Test
    public void flipTest() {
        ByteBuffer buffer = ByteBuffer.allocate(128);
        // 写入3个字节
        buffer.put((byte) 'a');
        buffer.put((byte) 'b');
        buffer.put((byte) 'c');
        assertEquals(128, buffer.capacity());
        assertEquals(3, buffer.position());
        assertEquals(128, buffer.limit());
        assertEquals(128 - 3, buffer.remaining());

        // flip, 进入读模式, flip是幂等的
        buffer.flip();

        assertEquals(0, buffer.position());
        assertEquals(3, buffer.limit());
        assertEquals(3, buffer.remaining());

        byte b = buffer.get();
        assertEquals('a', b);
        assertEquals(1, buffer.position());
        assertEquals(3, buffer.limit());
        assertEquals(3 - 1, buffer.remaining());
        b = buffer.get();
        assertEquals('b', b);
        b = buffer.get();
        assertEquals('c', b);

        try {
            b = buffer.get();
        } catch (BufferUnderflowException e) {
            e.printStackTrace();
        }
        // 读到结束了
        assertEquals(3, buffer.position());
        assertEquals(3, buffer.limit());
        assertEquals(3 - 3, buffer.remaining());

        // 重新开始, 从头读
        buffer.flip();
        b = buffer.get();
        assertEquals('a', b);
        b = buffer.get();
        assertEquals('b', b);
        b = buffer.get();
        assertEquals('c', b);

        // 读到结束了
        assertEquals(3, buffer.position());
        assertEquals(3, buffer.limit());
        assertEquals(3 - 3, buffer.remaining());

        // 接着写
        buffer.limit(buffer.capacity());
        assertEquals(3, buffer.position());
        assertEquals(128, buffer.limit());
        assertEquals(128 - 3, buffer.remaining());

        buffer.put((byte) 'd');
        buffer.put((byte) 'e');
        buffer.put((byte) 'f');
        assertEquals(6, buffer.position());
        assertEquals(128, buffer.limit());
        assertEquals(128 - 6, buffer.remaining());

        // 只能从头读
        buffer.flip();
        assertEquals(0, buffer.position());
        assertEquals(6, buffer.limit());
        assertEquals(6 - 0, buffer.remaining());
    }

    @Test
    public void sliceTest() {

    }

    public static String getString(ByteBuffer buffer) {
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
            // charBuffer = decoder.decode(buffer);//用这个的话，只能输出来一次结果，第二次显示为空
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

}
