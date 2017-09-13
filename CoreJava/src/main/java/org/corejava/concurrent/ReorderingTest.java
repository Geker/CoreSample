package org.corejava.concurrent;

public class ReorderingTest {
	private int a = 1;
	private boolean flg = false;

	public void method1() {
		a = 2;
		flg = true;

	}

	public void method2() {
		if (flg) {
			if (a != 2)
				System.out.println("a = " + a);
		}
	}

	public static void main(String[] args) {

		for (int i = 0; i < 1000000; i++) {

			ReorderingTest reorderExample = new ReorderingTest();

			Thread thread1 = new Thread(() -> {
				reorderExample.method1();
			});

			Thread thread2 = new Thread(() -> {
				reorderExample.method2();
			});

			thread1.start();
			thread2.start();
		}
	}
}