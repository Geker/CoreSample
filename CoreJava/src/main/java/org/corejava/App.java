package org.corejava;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        byte[] bytes = "abc".getBytes();
        System.err.println(ReflectionToStringBuilder.toString(bytes));
    }
}
