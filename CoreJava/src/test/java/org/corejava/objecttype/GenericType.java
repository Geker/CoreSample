package org.corejava.objecttype;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class GenericType {
    @Test
    public void judgeArrayListType() {

        ArrayList<Integer> arrListInt = new ArrayList<>();
        ArrayList<Long> arrListLong = new ArrayList<>();
        System.out.println(arrListInt.getClass());
        System.out.println(arrListLong.getClass());
        assertEquals(arrListInt.getClass(), arrListLong.getClass());

    }

}
