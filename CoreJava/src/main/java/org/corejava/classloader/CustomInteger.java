package org.corejava.classloader;

public class CustomInteger extends Number {

  private static final long serialVersionUID = 2131321L;

  public void runMe() {
	System.out.println("CustomInteger class runMe");
  }

  @Override
  public int intValue() {
	return 0;
  }

  @Override
  public long longValue() {
	return 0;
  }

  @Override
  public float floatValue() {
	return 0;
  }

  @Override
  public double doubleValue() {
	return 0;
  }

}
