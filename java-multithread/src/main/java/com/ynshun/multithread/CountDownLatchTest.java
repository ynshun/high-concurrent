package com.ynshun.multithread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * public void await() throws InterruptedException { }; //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行 
 * 
 * 
 * public boolean await(long timeout, TimeUnit unit) throws InterruptedException { }; //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行 
 * 
 * 
 * public void countDown() { }; // 将count值减1
 * 
 * @ClassName: Test
 * @Description:
 * @author: ynshun
 * @date: 2018年1月30日 下午1:25:29
 */
public class CountDownLatchTest {
	public static int i = 0;

	public static void main(String[] args) throws Exception {
		final CountDownLatch latch = new CountDownLatch(2);

		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i++;
				// latch.countDown();
			};
		}.start();

		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i++;
				// latch.countDown();
			};
		}.start();

		// latch.await();
		latch.await(1, TimeUnit.SECONDS);
		System.err.println(i);
		// Thread.sleep(2000);
		// System.err.println(i);
		
	}
}