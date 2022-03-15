package edu.xmu.test.javax.netty;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {

    @Test
    public void name() throws IOException {
        RandomAccessFile file = new RandomAccessFile("/Users/davywalker/Downloads/All_Beauty.json", "r");
        FileChannel channel = file.getChannel();
        try {
            ByteBuffer buff = ByteBuffer.allocate(128);
            int read = 1;
            while (read > 0) {
                read = channel.read(buff);
                buff.flip();
                String str = new String(buff.array());
                System.out.print(str);
                buff.clear();
//                buff.flip();
            }
        } finally {
            channel.close();
        }
    }

}
