package org.corejava;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args) throws IOException
    {


      System.out.println(Runtime.class.getPackage().getImplementationVersion());
        List<String> s = FileUtils.readLines(new File("D:\\1"), "GBK");
        System.err.println(s);
        String str = s.get(0);
        String[] strs = StringUtils.split(str, ",");
        byte[] ss    = new byte[strs.length];
        File f = new File("D:\\2");
        FileOutputStream fs = new FileOutputStream(f);
        for (String s1 : strs)
        {
            byte[] data = new byte[1];
            data[0] = Byte.valueOf(s1);
            IOUtils.write(data, fs);

        }
        fs.close();


    }
}
