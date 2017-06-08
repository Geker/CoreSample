package org.corejava.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;

public class NIOFILE {
    // NIO的fileChannel不能设置为非阻塞模式，因此无法和selector配合进行相关的操作。
    public static void main(String[] args) throws IOException {
		File f = new File("CoreJava.iml");
        FileInputStream fis = new FileInputStream(f);
        FileChannel fc = fis.getChannel();
        Selector selector = Selector.open();
        ByteBuffer dst = ByteBuffer.allocate(1024);
        fc.read(dst);
        dst.flip();
        byte[] bytes = dst.array();
        System.err.println(new String(bytes));


     }
}
