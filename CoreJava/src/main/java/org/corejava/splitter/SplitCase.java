package org.corejava.splitter;

import java.util.function.Consumer;

import org.junit.Test;

import com.google.common.base.Splitter;

//Splitter 可以将特定的字符串规律地转换为map，或者list并实现Iterator
public class SplitCase {

    @Test
    public void splitTest() throws Exception {
        String src = "A=B&C=D&E=F";
        Splitter.fixedLength(3).split(src).forEach(new Consumer<String>() {

            @Override
            public void accept(String t) {
                System.out.println(t);
            }
        });

        Splitter.on('&').split(src).forEach(new Consumer<String>() {

            @Override
            public void accept(String t) {
                System.out.println(t);
            }
        });

        Splitter.on('&').withKeyValueSeparator('=').split(src);

    }

}
