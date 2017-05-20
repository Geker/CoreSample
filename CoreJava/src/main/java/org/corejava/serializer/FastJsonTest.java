package org.corejava.serializer;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class FastJsonTest {

    @Test
    public void JsonToString() throws Exception {

        // 基本类型不会添加"";

        System.out.println(JSON.toJSONString(Integer.valueOf(1900)));
        // String 会添加"",然后后续的再次toJSonString会添加\\
        System.out.println(JSON.toJSONString(JSON.toJSONString("hello world")));
        System.out.println(JSON.toJSONString(Long.valueOf(1000)));

    }

}
