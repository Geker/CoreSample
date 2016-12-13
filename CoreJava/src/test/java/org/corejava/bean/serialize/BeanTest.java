package org.corejava.bean.serialize;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class BeanTest {


    @Test
    public void testSetName() throws Exception {
        Bean b = new Bean();
        b.setName("2015");
        String str = JSON.toJSONString(b);
        Bean bb = JSON.parseObject(null, Bean.class);
        System.out.println(bb);
    }

}
