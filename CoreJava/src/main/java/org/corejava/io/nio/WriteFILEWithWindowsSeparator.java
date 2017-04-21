package org.corejava.io.nio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class WriteFILEWithWindowsSeparator {
    public static void main(String[] args) throws IOException {
        File f = new File("test");
        List<String>list=new ArrayList<>();
        list.add("hello");
        list.add("world");
        FileUtils.writeLines(f, "GBK", list, IOUtils.LINE_SEPARATOR_WINDOWS);
     }
}
