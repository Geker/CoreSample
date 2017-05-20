package org.corejava;
class Test {
    public static void main(String[] args) {
        try {
            throw new NullPointerException();
        } catch (NullPointerException npe) {
            System.out.println("In catch");
        } finally {
            System.out.println("In finally");
        }
    }
}