package org.corejava.classloader;

import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class ClassloaderTest {
    @Test
    public void showClassloader() {

        System.out.println("class loader for Integer: " + Integer.class.getClassLoader());
        System.out.println("class loader for BlowfishCipher: " + com.sun.crypto.provider.BlowfishCipher.class.getClassLoader());
        System.out.println("class loader for this class: " + ClassloaderTest.class.getClassLoader());

    }

    @Test
    public void ownclassloader() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException {
        CustomClassLoader loader = new CustomClassLoader(ClassloaderTest.class.getClassLoader());
        Class clazz = loader.loadClass("org.corejava.classloader.CustomInteger");
        Object instance = clazz.newInstance();
        clazz.getMethod("runMe").invoke(instance);
        System.out.println(clazz.getClassLoader());

    }

}
