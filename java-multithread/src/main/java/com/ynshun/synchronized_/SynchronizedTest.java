package com.ynshun.synchronized_;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedTest {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		
		SynchronizedTest test = new SynchronizedTest();
		for (int i = 0; i < 50; i++) {
			threadPool.submit(new Runnable() {
				public void run() {
					test.method2();
					// SynchronizedTest.method3();
				}
			});
		}
	}

	/**
	 * 锁住的是该对象,类的其中一个实例，当该对象(仅仅是这一个对象)在不同线程中执行这个同步方法时，线程之间会形成互斥。达到同步效果，
	 * 但如果不同线程同时对该类的不同对象执行这个同步方法时，则线程之间不会形成互斥，因为他们拥有的是不同的锁。
	 */
	public synchronized void method1() {
		System.out.println(Thread.currentThread().getId() + " <> " + Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 */
	public void method2() {
		System.out.println(Thread.currentThread().getId() + " <> " + Thread.currentThread().getName() + "---进来了");
		
		
		synchronized(this) {
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.err.println(Thread.currentThread().getId() + " <> " + Thread.currentThread().getName() + "---执行了");
		}
		System.out.println(Thread.currentThread().getId() + " <> " + Thread.currentThread().getName() + "---执行完成");
	}
	
	
	/**
	 * 锁住的是该类，当所有该类的对象(多个对象)在不同线程中调用这个static同步方法时，线程之间会形成互斥，达到同步效果。
	 */
	public synchronized static void method3() {
		System.out.println(Thread.currentThread().getId() + " <> " + Thread.currentThread().getName());
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
