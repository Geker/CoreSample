package org.corejava.bean.serialize;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanTest {


    @Test
    public void testSetName() throws Exception {
        Bean b = new Bean();
        b.setName("2015");
        String str = JSON.toJSONString(b);
        Bean bb = (Bean) JSON.parseObject(str, Object.class);

        System.out.println(bb);
    }

    @Test
    public void testJacksonSetName() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Bean b = new Bean();
        b.setName("2015");
        String str = mapper.writeValueAsString(b);

        // String str = JSON.toJSONString(b);
        Bean bb = (Bean) mapper.readValue(str, Object.class);

        System.out.println(bb);
    }


}
