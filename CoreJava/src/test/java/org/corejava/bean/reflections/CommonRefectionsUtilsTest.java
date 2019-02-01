package org.corejava.bean.reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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

    /**
     * MethodUtils无法调用静态内部方法
     *
     * @throws Exception
     */
    @Test
    public void staticInvoke() throws Exception {
        MethodUtils.invokeStaticMethod(Computer.class, "printClassName", new Object[0]);
    }

    @Test
    public void staticInvoke2() throws Exception {
        Method m = MethodUtils.getAccessibleMethod(Computer.class, "printClassName", new Class<?>[0]);
        m.invoke(null, new Object[0]);
    }

    @Test
    public void staticInvoke3() throws Exception {
        Method m = MethodUtils.getMatchingMethod(Computer.class, "printClassName", new Class<?>[0]);
        m.setAccessible(true);
        m.invoke(null, new Object[0]);
    }

    @Test
    public void modifyStaticFinalInt() throws Exception {
        Field m = FieldUtils.getDeclaredField(Computer.class, "i", true);
        // m.setAccessible(true);
        // m.setInt(null, 9);
        setFinalStatic(m, 11);
        System.out.println(m.get(null));
    }

    @Test
    public void modifyStaticFinalBoolean() throws Exception {
        Field m = FieldUtils.getDeclaredField(Computer.class, "b", true);
        // m.setAccessible(true);
        // m.setInt(null, 9);
        setFinalStatic(m, false);
        System.out.println(m.get(null));
    }

    /*
     * 通过修改modifier可以间接修改final的值
     */
    static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }

}
