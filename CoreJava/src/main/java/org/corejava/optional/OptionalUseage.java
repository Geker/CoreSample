package org.corejava.optional;

import java.util.Optional;

public class OptionalUseage {

    public static void main(String[] args) {
        Optional<String> opt=Optional.of("hello");
        Optional<String> opt1=Optional.ofNullable(null);
        opt.ifPresent(s->{System.out.println(s);});
        opt1.ifPresent(s->{System.out.println(s);});
    }
}
