package org.algorithm.linear;

public class CustomStack<T> {
    LinkedArray<T> array = new LinkedArray<>();

    public T pop() {
        T value = array.getIndex(array.size() - 1);
        array.delTail();
        return value;
    }

    public void push(T value) {
        array.addTail(value);
    }
}
