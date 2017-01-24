package org.corejava.bean.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanUtilsComputerTest {
    Computer com = new Computer();
    ObjectMapper mapper = new ObjectMapper();
    @Before
    public void init()
    {
        CorePart corePart = new CorePart();
        corePart.setCpu("Intel I7-7700K");
        corePart.setMem("DDR4 16g");
        corePart.setDisk("SAMSUNG PM 961");
        com.setCorePart(corePart);
        com.setKeyboard("PS/2 KeyBoard");
        com.setMouse("Logiteck Mouse");
        Instant inst = LocalDateTime.now().minusDays(2).toInstant(ZoneOffset.UTC);
        com.setBuyTime(Date.from(inst));
        com.setPrograms(new String[] { "eclipse", "Browser", "notepad" });
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", "java");
        map.put("search", "bing.com");
        map.put("explorer", "TOTCMD");
        com.setOthersParts(map);
    }

    @Test
    public void testPropertyUtils_getProperty() throws Exception {

        // Test SimpleProperty
        Object simpleobj = PropertyUtils.getSimpleProperty(com, "keyboard");
        Assert.assertEquals(simpleobj, com.getKeyboard());
        // Test IndexedProperty
        Object indexedObj = PropertyUtils.getIndexedProperty(com, "programs[1]");
        Assert.assertEquals(indexedObj, com.getPrograms()[1]);

        // Test mapperedProperty
        Object mapperedObj = PropertyUtils.getMappedProperty(com, "othersParts(code)");

        Assert.assertEquals(mapperedObj, com.getOthersParts().get("code"));

        // Test nestedProperty
        Object nestedObj = PropertyUtils.getNestedProperty(com, "corePart.cpu");
        Assert.assertEquals(nestedObj, com.getCorePart().getCpu());

        // Test nestedProperty
        Object obj = PropertyUtils.getProperty(com, "corePart.cpu");
        Assert.assertEquals(obj, com.getCorePart().getCpu());

    }

    @Test
    public void LazyDynaBean() throws Exception {
        DynaBean dynaBean = new LazyDynaMap();        // create DynaBean

        dynaBean.set("foo", "bar");                   // simple
        dynaBean.set("customer", "title", "Mr");      // mapped
        dynaBean.set("address", 0, "addressLine1");     // indexed
        dynaBean.set("address", 1, "addressLine2"); // indexed
        dynaBean.set("address", 2, "addressLine3"); // indexed

        Map myMap = ((LazyDynaMap) dynaBean).getMap(); // retrieve the Map

        System.err.println(JSON.toJSONString(myMap));
    }

    @Test
    public void bean2MapTest() throws Exception {
        Object obj = PropertyUtils.describe(com);
        System.err.println((com));
        System.err.println(JSON.toJSONString(obj));
        System.err.println(mapper.writeValueAsString(obj));
        System.err.println("---------");
        Object obj2 = BeanUtils.describe(com);
        System.err.println(JSON.toJSONString(obj2));
        System.err.println(mapper.writeValueAsString(obj2));

    }
}
