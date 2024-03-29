package org.corejava.concurrent;

/**
 * 证明在JAVA x86(x64)处理器上，32位JVM Long的读取和写入，是可能分段的。 <br/>
 * 但是在64位 JVM上，long不分段，是原子操作
 *
 * @author songqingqing
 *
 */
public class AtomicLongAccessTest {

	protected long longVal = -1l;

	public static void main(String[] args) {
		System.out.println(toBinary(-1l));
		System.out.println(toBinary(1l));
		AtomicLongAccessTest t = new AtomicLongAccessTest();
		Worker w1 = new Worker(t);
		Worker1 w2 = new Worker1(t);
		w1.setDaemon(true);
		w2.setDaemon(true);
		w1.start();
		w2.start();
		long val = 0;
		while (true) {
			// read the long value
			val = t.longVal;
			if (val != -1l && val != 1l) {

				System.out.println("long access is not atomic!");
				System.out.println(toBinary(val));
				break;
			}
		}
	}

	private static String toBinary(long l) {
		StringBuilder sb = new StringBuilder(Long.toBinaryString(l));
		while (sb.length() < 64) {
			sb.insert(0, "0");
		}
		return sb.toString();
	}

	static class Worker extends Thread {

		public Worker(AtomicLongAccessTest t) {
			this.t = t;
		}

		private AtomicLongAccessTest t;

		@Override
		public void run() {
			while (true) {
				t.longVal = -1l;
			}
		}
	}

	static class Worker1 extends Thread {

		public Worker1(AtomicLongAccessTest t) {
			this.t = t;
		}

		private AtomicLongAccessTest t;

		@Override
		public void run() {
			while (true) {
				t.longVal = 1l;
			}
		}

	}
}
