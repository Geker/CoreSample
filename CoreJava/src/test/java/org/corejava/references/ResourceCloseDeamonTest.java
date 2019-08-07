package org.corejava.references;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ResourceCloseDeamonTest {

    @Test
    public void testResourceCloseDeamon() throws IOException {

        resUse();

        ResourceCloseDeamon rd = new ResourceCloseDeamon();
        rd.run();
    }

    private void resUse() throws IOException {
        String name = new String("D:/tmp/test");
        FileOutputStream fos = FileUtils.openOutputStream(new File(name));
        fos.write("hello world".getBytes());
        ResourceCloseDeamon.register(name, Arrays.asList(fos));

    }
}
