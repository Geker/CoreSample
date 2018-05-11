package org.corejava.classloader;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class ClassloaderTest {
    @SuppressWarnings("restriction")
    @Test
    public void showClassloader() {

        System.out.println("class loader for Integer: " + Integer.class.getClassLoader());
        System.out.println("class loader for BlowfishCipher: " + com.sun.crypto.provider.BlowfishCipher.class.getClassLoader());
        System.out.println("class loader for this class: " + ClassloaderTest.class.getClassLoader());

    }

    // 不同Classloader加载相同的类，其hashCode不一样。
    @Test
    public void ownclassloaderTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        CustomClassLoader loader = new CustomClassLoader(ClassloaderTest.class.getClassLoader());
        Class<?> clazz = loader.loadClass("org.corejava.classloader.CustomInteger");
        Object instance = clazz.newInstance();
        clazz.getMethod("runMe").invoke(instance);
        System.out.println(clazz.getClassLoader());
        System.err.println(String.format("class:%s;HashCode:%s", clazz, clazz.hashCode()));
        Class<?> clazz1 = ClassLoader.getSystemClassLoader().loadClass("org.corejava.classloader.CustomInteger");
        System.err.println(String.format("class:%s;HashCode:%s", clazz1, clazz1.hashCode()));
    }

}
