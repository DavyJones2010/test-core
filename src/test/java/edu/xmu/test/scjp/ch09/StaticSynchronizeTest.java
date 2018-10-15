package edu.xmu.test.scjp.ch09;

import org.junit.Test;

public class StaticSynchronizeTest {
	@Test
	public void synchronizeTest() throws InterruptedException {
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					DummyUtil.doSomeThing();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				try {
					DummyUtil.doSomeThing();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread t3 = new Thread() {
			@Override
			public void run() {
				try {
					DummyUtil.doSomeThing();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread t4 = new Thread() {
			@Override
			public void run() {
				try {
					DummyUtil.doOtherThing();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread t5 = new Thread() {
			@Override
			public void run() {
				try {
					DummyUtil.doOtherThing();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread t6 = new Thread() {
			@Override
			public void run() {
				try {
					new DummyUtil().doAdditionalThing();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread t7 = new Thread() {
			@Override
			public void run() {
				try {
					new DummyUtil().doAdditionalThing();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();

		t.join();
		t2.join();
		t3.join();
		t4.join();
		t5.join();
		t6.join();
		t7.join();
	}

	/**
	 * synchronized static method/block does not interfere with non-synchronized
	 * static method/block or instance method
	 */
	static class DummyUtil {
		/**
		 * synchronized means DummyUtil.class is locked
		 */
		synchronized static void doSomeThing() throws InterruptedException {
			System.out.println(Thread.currentThread().getName()
					+ " start doSomeThing");
			Thread.sleep((long) (Math.random() * 1000));
			System.out.println(Thread.currentThread().getName()
					+ " finished doSomeThing");
		}

		static void doLeftThing() throws InterruptedException {
			synchronized (DummyUtil.class) {
				System.out.println(Thread.currentThread().getName()
						+ " start doSomeThing");
				Thread.sleep((long) (Math.random() * 1000));
				System.out.println(Thread.currentThread().getName()
						+ " finished doSomeThing");
			}
		}

		/**
		 * No need to get DummyUtil.class's intrinsic lock
		 */
		static void doOtherThing() throws InterruptedException {
			System.out.println(Thread.currentThread().getName()
					+ " start doOtherThing");
			Thread.sleep((long) (Math.random() * 1000));
			System.out.println(Thread.currentThread().getName()
					+ " finished doOtherThing");
		}

		void doAdditionalThing() throws InterruptedException {
			System.out.println(Thread.currentThread().getName()
					+ " start doAdditionalThing");
			Thread.sleep((long) (Math.random() * 1000));
			System.out.println(Thread.currentThread().getName()
					+ " finished doAdditionalThing");
		}
	}

}
