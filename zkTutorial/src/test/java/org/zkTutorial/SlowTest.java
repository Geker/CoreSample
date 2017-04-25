package org.zkTutorial;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

public class SlowTest {

    @Test
    public void test() throws UnknownHostException {

        // System.err.println(longToIP(-1062717439L));
        InetAddress host = InetAddress.getLocalHost();
        System.err.println(host);
        // 配置hosts之后，getCanonicalHostName变快
        String a = host.getCanonicalHostName();
        System.err.println(a);

    }

    // 将十进制整数形式转换成127.0.0.1形式的ip地址
    public static String longToIP(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24 & 0xff)));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }
}
