package org.corejava;

public class ExceptionThrowsTest {

    public static void main(String[] args) {
        int j = getResult();
        System.out.println("result:" + j);

    }

    public static int getResult() {
        boolean flag = true;
        int x = 100;
        try{

            System.out.println( "try begin");
            if (flag)
                throw new RuntimeException("exception!");
            System.out.println("try begin2");
            return x;
        }
        catch (Exception e) {
            System.out.println("catch exception!");
            if (!flag)
                throw e;
            x = 99;
            return x;

        }
        finally {
            System.out.println("final");
            // return 3;
        }

    }
}
