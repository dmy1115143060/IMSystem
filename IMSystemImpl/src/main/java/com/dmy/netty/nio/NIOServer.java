package com.dmy.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by DMY on 2018/9/30 21:18
 */
public class NIOServer {
    public static void main(String[] args) throws Exception {
        Selector serverSelector = Selector.open();
        Selector clientSelector = Selector.open();

        // 新建一个线程，该线程主要用于处理客户端的连接请求
        new Thread(() -> {
            try {
                // 创建一个通道来监听客户端的连接,并向serverSelector进行注册。由serverSelector
                // 来处理客户端的连接请求
                ServerSocketChannel listenerChannel = ServerSocketChannel.open();
                listenerChannel.bind(new InetSocketAddress(8000));
                listenerChannel.configureBlocking(false);
                listenerChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
                while (true) {
                    // 判断当前是否有新连接，阻塞时间为1ms
                    if (serverSelector.select(1) > 0) {
                        Set<SelectionKey> selectionKeySet = serverSelector.selectedKeys();
                        Iterator<SelectionKey> selectionKeyIterator = selectionKeySet.iterator();
                        while (selectionKeyIterator.hasNext()) {
                            SelectionKey selectionKey = selectionKeyIterator.next();
                            // 对于每次的客户端连接，不再是一个连接对应一个线程，而是将对应的连接直接注册到clientSelector中，
                            // 由clientSelector来完成多个连接的批量处理
                            if (selectionKey.isAcceptable()) {
                                try {
                                    SocketChannel clientChannel = ((ServerSocketChannel)selectionKey.channel()).accept();
                                    clientChannel.configureBlocking(false);
                                    clientChannel.register(clientSelector, SelectionKey.OP_READ);
                                } finally {
                                    selectionKeyIterator.remove();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // 新建一个线程，该线程主要用于处理已经连接的客户端的读写数据请求
        new Thread(() -> {
            try {
                while (true) {
                    // 批量轮询有哪些连接可以进行数据读取
                    if (clientSelector.select(1) > 0) {
                        Set<SelectionKey> selectionKeySet = clientSelector.selectedKeys();
                        Iterator<SelectionKey> keyIterator = selectionKeySet.iterator();
                        while (keyIterator.hasNext()) {
                            SelectionKey key = keyIterator.next();
                            if (key.isReadable()) {
                                try {
                                    SocketChannel socketChannel = (SocketChannel) key.channel();
                                    // 以数据块为单位来读取数据
                                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                                    socketChannel.read(buffer);
                                    buffer.flip();
                                    System.out.println(Charset.defaultCharset().newDecoder().decode(buffer).toString());
                                } finally {
                                    keyIterator.remove();
                                    key.interestOps(SelectionKey.OP_READ);
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
