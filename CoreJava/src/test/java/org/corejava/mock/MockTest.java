package org.corejava.mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.LinkedList;

import org.junit.Test;

public class MockTest {

    @Test
    public void test() {
        @SuppressWarnings("unchecked")
        LinkedList<String> mockedList = mock(LinkedList.class);
        when(mockedList.getFirst()).thenReturn("1999");
        System.err.println(mockedList.getFirst());
        mockedList.add("222");
        verify(mockedList, times(1)).add("222");

    }

}
