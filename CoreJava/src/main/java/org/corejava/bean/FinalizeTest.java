package org.corejava.bean;

import java.io.IOException;

public class FinalizeTest {

    public static void main(String[] args) throws IOException {
        FinalizeTest f = new FinalizeTest();
        for (int i = 0; i < 100; i++)
            f.f();
        System.gc();
        System.in.read();
    }
    
    void f()
    {
        Computer com = new Computer();
        com.setCpu("intel");
        com.setMem("12G");
        System.out.println(com);
    }
}
