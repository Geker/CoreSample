package org.corejava.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NIOSocketSvr {
    static ExecutorService es = Executors.newFixedThreadPool(10);

    private static final Logger logger = LoggerFactory.getLogger(NIOSocketSvr.class);

    static class NServer extends Thread {
        int port;

        public NServer(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try {
                ServerSocketChannel ssc;

                ssc = ServerSocketChannel.open();
                ssc.configureBlocking(false);
                SocketAddress nsa = new InetSocketAddress("localhost", port);
                ssc.bind(nsa);
                Selector sel = Selector.open();
                ssc.register(sel, SelectionKey.OP_ACCEPT);

                while (true) {
                    int i = sel.select();
                    if (i > 0) {
                        Set<SelectionKey> keys = sel.selectedKeys();
                        Iterator<SelectionKey> ikey = keys.iterator();
                        while (ikey.hasNext()) {
                            SelectionKey key = ikey.next();
                            try {
                                if (key.isAcceptable()) {
                                    ServerSocketChannel localssc = (ServerSocketChannel) key.channel();
                                    SocketChannel accept = localssc.accept();
                                    handleSocketWithOutBlockMode(accept, sel);

                                }
                                if (key.isReadable()) {
                                    SocketChannel sc = (SocketChannel) key.channel();
                                    handleSocketRead(sc);
                                }
                                if (key.isWritable()) {
                                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                                    byteBuffer.flip();

                                    SocketChannel socketChannel = (SocketChannel) key.channel();
                                    socketChannel.write(byteBuffer);
                                    if (byteBuffer.hasRemaining()) {
                                        key.interestOps(SelectionKey.OP_READ);
                                    }
                                    byteBuffer.compact();
                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            finally {
                                ikey.remove();
                            }

                        }
                    }
                    else {

                    }
                }
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 接收到socket后，同步发送接收
     * 
     * @param sc
     */
    public static void handleSocketRead(SocketChannel sc) {
        ByteBuffer dst = ByteBuffer.allocate(512);
        es.submit(new Runnable() {

            @Override
            public void run() {
                try {
                    dst.clear(); // 写入之前，clear，清空内容
                    int i;
                    i = sc.read(dst);
                    if (i > 0) {
                        logger.debug("accept socket");
                        dst.flip(); // 读取之前flip，准备读取数据
                        byte[] bytes = new byte[i];
                        dst.get(bytes, 0, i);
                        logger.debug("recv:" + new String(bytes));
                        dst.clear();// 发送之前，clear，清空内容，开始写入
                        logger.debug(("echo:" + new String(bytes)));
                        dst.put(("echo:" + new String(bytes)).getBytes());
                        dst.flip();// 真正write的时候，实际上是读取，因此需要flip
                        sc.write(dst);

                    }
                }
                catch (IOException e) {

                    e.printStackTrace();

                    try {
                        sc.close();
                    }
                    catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }

            }
        });
    }

    /**
     * 接收到socket后，同步发送接收
     * 
     * @param sc
     */
    public static void handleSocketWithBlockMode(SocketChannel sc) {
        ByteBuffer dst = ByteBuffer.allocate(512);
        es.submit(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        dst.clear(); // 写入之前，clear，清空内容
                        int i = sc.read(dst);
                        if (i > 0) {
                            logger.debug("accept socket");
                            dst.flip(); // 读取之前flip，准备读取数据
                            byte[] bytes = new byte[i];
                            dst.get(bytes, 0, i);
                            logger.debug("recv:" + new String(bytes));
                            dst.clear();// 发送之前，clear，清空内容，开始写入
                            dst.put(("echo:" + new String(bytes)).getBytes());
                            dst.flip();// 真正write的时候，实际上是读取，因此需要flip
                            sc.write(dst);
                        }

                    }
                    catch (IOException e) {

                        e.printStackTrace();
                    }
                }

            }
        });

    }

    /**
     * 接收到socket后，同步发送接收
     * 
     * @param sc
     * @throws IOException
     */
    public static void handleSocketWithOutBlockMode(SocketChannel sc, Selector selector) throws IOException {
        sc.configureBlocking(false);
        sc.register(selector, SelectionKey.OP_WRITE);
        sc.register(selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) {
        NServer nss = new NServer(1024);
        nss.start();
    }
}
