package org.corejava.bean.beaninfo;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

import org.corejava.bean.NoteBook;

public class BeanInfoSample {

    public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        BeanInfo info;
        try {
            NoteBook nb=new NoteBook();
            nb.setCpu("intel");

            info = Introspector.getBeanInfo(NoteBook.class);
            // 获得所有的属性。
            PropertyDescriptor[] pds = info.getPropertyDescriptors();
            Object s = pds[1].getReadMethod().invoke(nb, null);
            System.out.println(s);
            System.out.println(info.getDefaultPropertyIndex());
            BeanInfo[] ss = info.getAdditionalBeanInfo();
        }
        catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
