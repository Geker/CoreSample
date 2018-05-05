package org.corejava.bytebubby;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;

public class App {
    public static void main(String[] args) {
        ByteBuddy bb = new ByteBuddy();
        Unloaded<Object> type = bb.subclass(Object.class).name("org.corejava.bytebubby.dyn.Type").make();
        Loaded<Object> cls = type.load(App.class.getClassLoader());

        System.err.println(type.getTypeDescription());
        System.err.println(cls.getClass().getName());

    }
}
