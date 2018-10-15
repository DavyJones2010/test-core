package edu.xmu.test.designpattern.reactor;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.Test;

import static java.nio.file.StandardOpenOption.READ;

/**
 * Created by davywalker on 17/5/8.
 */
public class NioFileSample {
    @Test
    public void fileSample() throws Exception {
        Path path = FileSystems.getDefault().getPath("/Users/davywalker/logs", "audit.log");
        FileChannel fileChannel = FileChannel.open(path, READ);
        ByteBuffer buf = ByteBuffer.allocate(48);
        fileChannel.read(buf);
        System.out.println(new String(buf.array(), "UTF-8"));
        fileChannel.close();
    }
}
