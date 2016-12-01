package org.corejava.beanshell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import bsh.EvalError;
import bsh.Interpreter;

public class BshInvoke {
    public static void main(String[] args) throws FileNotFoundException, IOException, EvalError {
        Interpreter i = new Interpreter();
        InputStream url = BshInvoke.class.getResourceAsStream("bsh.bsh");
        i.eval(new InputStreamReader(url));
    }
}
