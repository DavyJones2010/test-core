package edu.xmu.test.designpattern.reactor;

import org.junit.Test;

import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * Created by davywalker on 16/10/20.
 */
public class SelectorTest {
    @Test
    public void selectorTest() throws Exception {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        SocketChannel socketChannel1 = SocketChannel.open();
        socketChannel1.configureBlocking(false);
        SocketChannel socketChannel2 = SocketChannel.open();
        socketChannel2.configureBlocking(false);

        socketChannel.register(selector, SelectionKey.OP_WRITE|SelectionKey.OP_READ, "socketChannel");
        socketChannel1.register(selector, SelectionKey.OP_WRITE|SelectionKey.OP_READ, "socketChannel1");
        socketChannel2.register(selector, SelectionKey.OP_WRITE|SelectionKey.OP_READ, "socketChannel2");

        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        for (SelectionKey selectionKey : selectionKeys) {
            if(selectionKey.isReadable()){
                SelectableChannel channel = selectionKey.channel();

            }else if(selectionKey.isWritable()){

            }
        }


    }
}
