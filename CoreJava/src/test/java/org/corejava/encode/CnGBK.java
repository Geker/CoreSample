package org.corejava.encode;

import org.junit.Test;

import junit.framework.TestCase;

public class CnGBK extends TestCase {

    /**
     * 如果字符的范围超过GBK的范围，会导致转换后变成问号。
     *
     * @throws Exception
     */
    @Test
    public void testOutOfGBK() throws Exception {
        String s = "⊙∀⊙！";
        s = s.replaceAll("[^\u4E00-\u9FA5\u3000-\u303F\uFF00-\uFFEF\u0000-\u007F\u201c-\u201d]", " ");

        byte[] bytes = s.getBytes("GBK");

        String sgbk = new String(bytes, "GBK");
        System.out.println(sgbk);

    }

}
