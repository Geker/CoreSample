package org.corejava.bean.proxy;

/**
 * Created by songqingqing on 11/18/2016.
 */
public  abstract   class AddClass  implements add{

    public int addC(int i, int j) {
        System.out.printf("before \n ");
        int n= this.addInt(i,j);
        System.out.printf("after \n");
        return n;
    }
}
