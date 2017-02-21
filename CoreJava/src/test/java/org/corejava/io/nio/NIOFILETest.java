package org.corejava.io.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Assert;
import org.junit.Test;

public class NIOFILETest {
    File file = new File("D:\\logs\\pafa.log.2016-11-23");
    File fileb = new File("D:\\logs\\pafa.log.BBB");
    @Test
    public void NIOFileRead() throws Exception {

        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(fileb);

        FileChannel fc = fis.getChannel();

        Assert.assertTrue(fc.isOpen());
        // ByteBuffer bb = ByteBuffer.allocateDirect(1024);
        ByteBuffer bb = ByteBuffer.allocate(1024);

        int len = 0;
        while ((len = fc.read(bb)) > 0) {
            bb.flip();
            byte[] arr = new byte[len];
            // if (bb.hasArray()) //如果使用allocateDirect，则hasArray为false。
            // arr = bb.array();
            bb.get(arr, 0, len);
            if(arr!=null)
            {
                fos.write(arr);
            }
            bb.clear();

        }
        fos.close();

    }
}
