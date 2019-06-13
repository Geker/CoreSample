package org.corejava.bytebuddy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Test;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

public class RedefineClassTest {

    @Test
    public void test() {
        ByteBuddyAgent.install();
        Foo foo = new Foo();
        new ByteBuddy().redefine(Bar.class).name(Foo.class.getName()).make().load(Foo.class.getClassLoader(),
            ClassReloadingStrategy.fromInstalledAgent());
        assertThat(foo.m(), is("bar"));
    }

    @Test
    public void testMethod() throws InstantiationException, IllegalAccessException {
        String toString = new ByteBuddy().subclass(Object.class).name("example.Type").make().load(getClass().getClassLoader()).getLoaded()
            .newInstance() // Java reflection API
            .toString();
        System.out.println(toString);
    }

    @Test
    public void testMethod_override() throws InstantiationException, IllegalAccessException {
        String toString = new ByteBuddy().subclass(Object.class).name("example.Type").method(named("toString"))
            .intercept(FixedValue.value("Hello World!")).make().load(getClass().getClassLoader()).getLoaded().newInstance().toString();
        System.out.println(toString);

    }

    @Test
    public void test_fixValue() throws Exception {

        Foo1 dynamicFoo = new ByteBuddy().subclass(Foo1.class).method(ElementMatchers.isDeclaredBy(Foo1.class))
            .intercept(FixedValue.value("One!")).make().load(getClass().getClassLoader()).getLoaded().newInstance();
        System.out.println(dynamicFoo.bar());
        System.out.println(dynamicFoo.foo());
        System.out.println(dynamicFoo.foo(this));
    }

    @Test
    public void testMultiOverrideMethod() throws Exception {

        Foo1 dynamicFoo = new ByteBuddy().subclass(Foo1.class)
            .method(ElementMatchers.isDeclaredBy(Foo1.class)).intercept(FixedValue.value("One!"))
            .method(ElementMatchers.named("foo")).intercept(FixedValue.value("Two!"))
            .method(ElementMatchers.named("foo").and(ElementMatchers.takesArguments(1))).intercept(FixedValue.value("Three!"))
           .make().load(getClass().getClassLoader()).getLoaded().newInstance();
        System.out.println(dynamicFoo.bar());
        System.out.println(dynamicFoo.foo());
        System.out.println(dynamicFoo.foo(this));
    }

    private ElementMatcher<? super MethodDescription> named(String string) {

        return ElementMatchers.named(string);
    }
}
