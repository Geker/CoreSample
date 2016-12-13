package org.corejava.bean.serialize;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;

public class JsonSerializeResult {
    static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test() throws JsonProcessingException {
        HashMap<String, String> map = new HashMap<>();
        // map.put("key", "hello");
        // System.out.println(mapper.writeValueAsString(map));
        Map<String, String> map1 = JSON.parseObject("{}", new TypeReference<Map<String, String>>() {
        });
        Map<String, String> map2 = Maps.newHashMap(map1);
        System.out.println(JSON.toJSON(map2));
    }

}
