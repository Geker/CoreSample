package org.corejava;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class APP {
    public static void main(String[] args) throws IOException {
        List l = new ArrayList<String>();
        int tot = Integer.parseInt(args[0]);
        for (int i = 0; i < tot; i++) {
            l.add(
                "asdhjkllllllllllllllllllllllldfsafffffffffffff"
                        + "ff3334444441111111111144333333333322222222222224444444444444433333333333333333333111111111111111111111114444444444444444433333333333333322222222222222222244444444444444444111111111111111111111555555555555555555555532333333333\n"
                        + i);
        }
        System.out.println(l.size());
        System.out.println(l.get(tot - 2));
        System.in.read();
    }
}
