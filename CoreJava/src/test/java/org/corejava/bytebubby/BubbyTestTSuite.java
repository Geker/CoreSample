package org.corejava.bytebubby;

import static org.junit.Assert.*;

import java.lang.instrument.Instrumentation;
import java.util.Map;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.Test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.agent.builder.AgentBuilder.RawMatcher;
import net.bytebuddy.description.TypeVariableSource.Visitor;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.ModifierContributor.ForMethod;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Builder;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.implementation.MethodCall.WithoutSpecifiedTarget;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.utility.JavaModule;

public class BubbyTestTSuite {
    static ByteBuddy bb = new ByteBuddy();

    @Test
    public void testNewType() {
        Unloaded<Object> type = bb.subclass(Object.class).name("org.corejava.bytebubby.dyn.Type").make();
        Loaded<Object> cls = type.load(this.getClass().getClassLoader(), ClassLoadingStrategy.Default.WRAPPER_PERSISTENT);
        System.out.println(cls.getClass().getClassLoader());

        System.out.println(type.getTypeDescription());
        System.out.println(cls.getLoaded().getName());
    }

    /**
     * @throws IllegalAccessException
     * @throws InstantiationException
     *
     */
    @Test
    public void testNewTypeClassLoadingStrategy() throws InstantiationException, IllegalAccessException {
        Unloaded<Object> type = bb.subclass(Object.class).name("org.corejava.bytebubby.dyn.Type").make();
        // load 方法，如果是injectClassloader，并且没有seal，那么类直接注入injectClassloader，否者其他情况，都新建一个新的classloader使用
        Loaded<Object> cls = type.load(BubbyTestTSuite.class.getClassLoader());
        Map<TypeDescription, byte[]> s = cls.getAllTypes();
        System.out.println(type.getTypeDescription());
        System.out.println(cls.getLoaded().newInstance().getClass().getClassLoader());
        System.out.println(cls.getLoaded().getName());
        System.out.println(cls.getLoaded().getClassLoader());
    }

    // 三种方式处理
    // new ByteBuddy().subclass(Foo.class) 子类
    // new ByteBuddy().redefine(Foo.class) 重定义
    // new ByteBuddy().rebase(Foo.class) 基于原方法扩展
    @Test
    public void testModifyMethod() throws Exception {
        String toString = new ByteBuddy().subclass(Object.class).name("example.Type").method(named("toString"))
            .intercept(FixedValue.value("Hello World!")).make().load(getClass().getClassLoader()).getLoaded().newInstance().toString();
        System.out.println(toString);
    }

    @Test
    public void testAddMethod() throws Exception {
        Object obj = new ByteBuddy().subclass(Object.class).name("example.Type").defineMethod("toStr", String.class, Visibility.PUBLIC)
            .intercept(FixedValue.value("Hello new Method!")).make().load(getClass().getClassLoader()).getLoaded().newInstance();
        System.out.println(MethodUtils.invokeExactMethod(obj, "toStr"));
    }

    private ElementMatcher<? super MethodDescription> named(String string) {

        return new ElementMatcher<MethodDescription>() {

            @Override
            public boolean matches(MethodDescription target) {
                System.err.println(target.asSignatureToken().getName());
                return target.asSignatureToken().getName().equals(string);

            }
        };
    }

}
