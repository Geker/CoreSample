package org.corejava.bean.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BeanUtilsTest {
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

        // PropertyUtils.describe不会转换属性的对象。map的value还为object
        Object obj = PropertyUtils.describe(com);
        System.err.println((com));
        System.err.println(JSON.toJSONString(obj));
        System.err.println(mapper.writeValueAsString(obj));
        System.err.println("---------");

        // BeanUtils.describe 会转换属性的对象。map的value被转换为String

        Object obj2 = BeanUtils.describe(com);
        System.err.println(JSON.toJSONString(obj2));
        System.err.println(mapper.writeValueAsString(obj2));

    }

    @Test
    public void map2Bean() throws Exception {


        Map map = PropertyUtils.describe(com);
        Computer newCom = new Computer();
        BeanUtils.populate(newCom, map);
        System.out.println(JSON.toJSONString(com));
        System.out.println(JSON.toJSONString(newCom));

    }

    @Test
    public void map2BeanWithDateConverter() throws Exception {
        BeanUtilsBean.setInstance(new BeanUtilsBean2());
        DateConverter dateCon = new DateConverter();
        dateCon.setPattern("yyyyMMdd");
        dateCon.setLocale(Locale.getDefault());
        BeanUtilsBean.getInstance().getConvertUtils().register(dateCon, Date.class);

        Map<String, Object> map = new HashMap<>();
        map.put("value", 101);
        map.put("curDate", 20170725);
        BeanWithDate b = new BeanWithDate();
        BeanUtils.populate(b, map);
        System.out.println(JSON.toJSONString(b));
    }

    @Test
    public void test_dateConverter() throws Exception {
        DateConverter dateCon = new DateConverter(null); // 带参数是，相当于设置default
        dateCon.setPattern("yyyyMMdd");
        dateCon.setLocale(Locale.getDefault());
        Date val = dateCon.convert(Date.class, 20170712);
        Date val1 = dateCon.convert(Date.class, null);

        System.out.println(val1);
    }

}
