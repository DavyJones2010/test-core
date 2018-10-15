package edu.xmu.test.javase.io;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.junit.Ignore;
import org.junit.Test;

public class NIOTest {
	// Buffers & Charsets & Channels & Selectors
	// Channel: FileChannel, DatagramChannel, SocketChannel, ServerSocketChannel
	// Buffers: ByteBuffer, CharBuffer, Double/Float/Int/Long/ShortBuffer,
	// MappedByteBuffer
	@Test
	@Ignore
	public void readFileTest() throws IOException {
		RandomAccessFile f = new RandomAccessFile(
				"src/test/resources/input.csv", "rw");
		FileChannel fc = f.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		int bytesRead = fc.read(buffer);

		while (bytesRead != -1) {
			System.out.println("Read " + bytesRead + " bytes");
			// Set limit=current, set current=0, for retrieve/write purpose
			buffer.flip();
			while (buffer.hasRemaining()) { // Whether current == limit
				System.out.print((char) buffer.get()); // current++
			}
			buffer.clear(); // set limit=1024, current=0
			bytesRead = fc.read(buffer);
		}
		f.close();
	}

	@Test
	public void bufferTest() {
		// Allocate Buffer: ByteBuffer.allocate(1024), CharBuffer.allocate(1024)
		// Write data to Buffer: FileChannel.read(buffer) or buffer.put('A')
		// capacity & position & limit
		// flip()/rewind()/clear()/compact()/get()/put(); mark()&reset()
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		buffer.put((byte) 0xCA);
		buffer.put((byte) 0xFE);
		buffer.put((byte) 0xBA);
		buffer.put((byte) 0xBE);
		assertEquals(4, buffer.position());
		assertEquals(1024, buffer.capacity());
		assertEquals(1024, buffer.limit());

		buffer.flip();
		assertEquals(0, buffer.position());
		assertEquals(1024, buffer.capacity());
		assertEquals(4, buffer.limit());

		buffer.get();
		assertEquals(1, buffer.position());
		assertEquals(1024, buffer.capacity());
		assertEquals(4, buffer.limit());

		buffer.compact();
		assertEquals(3, buffer.position());
		assertEquals(1024, buffer.capacity());
		assertEquals(1024, buffer.limit());

		buffer.put((byte) 0xFF);
		assertEquals(4, buffer.position());
		assertEquals(1024, buffer.capacity());
		assertEquals(1024, buffer.limit());

		buffer.flip();
		buffer.get();
		buffer.get();
		buffer.get();
		assertEquals(3, buffer.position());
		assertEquals(1024, buffer.capacity());
		assertEquals(4, buffer.limit());

		buffer.rewind();
		assertEquals(0, buffer.position());
		assertEquals(1024, buffer.capacity());
		assertEquals(4, buffer.limit());

		buffer.get();
		buffer.mark();
		buffer.get();
		buffer.get();
		buffer.reset();
		assertEquals(1, buffer.position());
		assertEquals(1024, buffer.capacity());
		assertEquals(4, buffer.limit());
	}

	@Test
	public void scattersTest() throws IOException {
		ByteBuffer header = ByteBuffer.allocate(128);
		ByteBuffer body = ByteBuffer.allocate(1024);
		ByteBuffer[] bufferArray = new ByteBuffer[] { header, body };

		RandomAccessFile f = new RandomAccessFile(
				"src/test/resources/input.csv", "rw");
		FileChannel fc = f.getChannel();
		fc.read(bufferArray);
		f.close();

		assertEquals(128, header.position());
		assertEquals(1024, body.position());
	}

	@Test
	@Ignore
	public void mappedByteBufferTest() throws IOException {
		Path filePath = FileSystems.getDefault().getPath(
				"src/test/resources/input.csv");
		FileChannel fileChannel = (FileChannel) Files.newByteChannel(filePath,
				StandardOpenOption.READ);
		MappedByteBuffer mappedByteBuffer = fileChannel.map(
				FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
		while (mappedByteBuffer.hasRemaining()) {
			System.out.print((char) mappedByteBuffer.get());
		}
		fileChannel.close();
	}
}