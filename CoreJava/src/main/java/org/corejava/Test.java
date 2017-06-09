package org.corejava;

import java.util.HashMap;

class Test {
    public static void main(String[] args) {
        try {

            HashMap<String, String> hm = new HashMap<>();
            hm.put("hello", "gell");


            throw new NullPointerException();
        } catch (NullPointerException npe) {
            System.out.println("In catch");
        } finally {
            System.out.println("In finally");
        }
    }
}