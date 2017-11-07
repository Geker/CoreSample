package org.corejava.classloader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClassloaderResource {
    public static void main(String[] args) throws IOException {

		InputStream is = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("org/corejava/classloader/333.xml");
        InputStreamReader isr = new InputStreamReader(is);
        char[] cbuf = new char[1024];
        isr.read(cbuf);

        isr.read(cbuf);
        System.err.println(new String(cbuf));

    }
}
