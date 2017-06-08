package org.corejava.test;

public class BetterProgrammerTask {

	public static Object[] reverseArray(Object[] a) {
		if (a != null) {
			Object[] c = new Object[a.length];
			int j = 0;
			for (int i = a.length; i > 0; i--) {
				c[j++] = a[i - 1];
			}
			return c;
		} else
			return null;
	}

}