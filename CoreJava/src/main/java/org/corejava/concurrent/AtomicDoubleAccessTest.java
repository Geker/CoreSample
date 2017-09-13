package org.corejava.concurrent;

/**
 * double 在64位jvm上是不分段的。 在32位jvm上待测试
 *
 * @author songqingqing
 *
 */
public class AtomicDoubleAccessTest {

	protected Double douVal = -1.5d;

	public static void main(String[] args) {
		System.out.println(toBinary(-1.5D));
		System.out.println(toBinary(1.5D));
		AtomicDoubleAccessTest t = new AtomicDoubleAccessTest();
		Worker w1 = new Worker(t);
		Worker1 w2 = new Worker1(t);
		w1.setDaemon(true);
		w2.setDaemon(true);
		w1.start();
		w2.start();
		double val = 0;
		while (true) {
			// read the long value
			val = t.douVal;
			if (val != -1.5d && val != 1.5d) {

				System.out.println("long access is not atomic!");
				System.out.println(toBinary(val));
				break;
			}
		}
	}

	private static String toBinary(double l) {
		StringBuilder sb = new StringBuilder(Long.toBinaryString(Double.doubleToLongBits(l)));
		while (sb.length() < 64) {
			sb.insert(0, "0");
		}
		return sb.toString();
	}

	static class Worker extends Thread {

		public Worker(AtomicDoubleAccessTest t) {
			this.t = t;
		}

		private AtomicDoubleAccessTest t;

		@Override
		public void run() {
			while (true) {
				t.douVal = -1.5D;
			}
		}
	}

	static class Worker1 extends Thread {

		public Worker1(AtomicDoubleAccessTest t) {
			this.t = t;
		}

		private AtomicDoubleAccessTest t;

		@Override
		public void run() {
			while (true) {
				t.douVal = 1.5D;
			}
		}

	}
}
