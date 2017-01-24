package org.corejava.bean.reflections;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.corejava.bean.utils.Computer;
import org.corejava.bean.utils.CorePart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommonRefectionsUtilsTest {
    Computer com = new Computer();

    @Before
    public void init() {
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
    public void functionInvoke() throws Exception {
        // 直接调用方法
        Date dt = (Date) MethodUtils.invokeMethod(com, true, "getTime");
        // 直接获取field
        Date dt2 = (Date) FieldUtils.readField(com, "buyTime", true);
        Assert.assertEquals(dt, dt2);
        System.err.println(com.getClass().hashCode());
     }
}
