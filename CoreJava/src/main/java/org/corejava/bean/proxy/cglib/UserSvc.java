package org.corejava.bean.proxy.cglib;

public class UserSvc {
  public void add() {
	System.out.println("This is add service");
  }

  public void delete(int id) {
	System.out.println("This is delete service：delete " + id);
  }
  static  public void   show(int num)
  {
	System.out.println("show number is : " + num);

  }
}
