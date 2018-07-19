package com.ynshun.lock;

public class SynchronizedTest {
	public void method1() {
		synchronized (SynchronizedTest.class) {
			System.out.println("方法1获得ReentrantTest的锁运行了");
			method2();
		}
	}

	public void method2() {
		synchronized (SynchronizedTest.class) {
			System.out.println("方法1里面调用的方法2重入锁,也正常运行了");
		}
	}

	/**
	 * synchronized的重入锁特性，即调用method1()方法时，已经获得了锁，此时内部调用method2()方法时，由于本身已经具有该锁，
	 * 所以可以再次获取。
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new SynchronizedTest().method1();
	}
}
