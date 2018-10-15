package edu.xmu.test.designpattern.reactor;

import io.netty.buffer.ByteBuf;
import org.springframework.expression.spel.ast.Selection;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by davywalker on 16/10/19.
 */
public class Handler implements Runnable {
    private static final int MININ = 128;
    private static final int MAXOUT = 1024;
    final SocketChannel socketChannel;
    final SelectionKey selectionKey;
    ByteBuffer input = ByteBuffer.allocate(MININ);
    ByteBuffer output = ByteBuffer.allocate(MAXOUT);

    static final int READING = 0, SENDING = 1;
    int state = READING;

    public Handler(Selector selector, SocketChannel accept) throws IOException {
        this.socketChannel = accept;
        socketChannel.configureBlocking(false);
        selectionKey = socketChannel.register(selector, 0);
        selectionKey.attach(this);
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    @Override
    public void run() {
        try {
            if (state == READING) read();
            else if (state == SENDING) send();
        } catch (IOException e) {
//
        }
    }

    void read() throws IOException {
        socketChannel.read(input);
        if (inputIsComlete()) {
            process();
            state = SENDING;
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }

    private void process() {

    }

    private boolean inputIsComlete() {
        return false;
    }

    void send() throws IOException {
        socketChannel.write(output);
        if (outputIsComplete()) {
            selectionKey.cancel();
        }
    }

    private boolean outputIsComplete() {
        return false;
    }
}
