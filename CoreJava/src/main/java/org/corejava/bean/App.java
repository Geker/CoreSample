package org.corejava.bean;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.corejava.bean.Computer.nest;

public class App {

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Computer c = new Computer();
        nest a = new nest("a");
        nest b = new nest("b");

        System.err.println(ReflectionToStringBuilder.toString(a));
        System.err.println(ReflectionToStringBuilder.toString(b));

        c.setCpu("intel");
        c.setKeyboard("coolmaster");
        c.setMem("1g");
        c.setMouse("logitech");
        NoteBook nb = new NoteBook();
        PropertyUtils.copyProperties(nb, c);
        BeanUtils.copyProperties(nb, c);
        System.err.println(ReflectionToStringBuilder.toString(nb));
        // System.err.println(MoreObjects.toStringHelper(nb).toString());

    }

}
