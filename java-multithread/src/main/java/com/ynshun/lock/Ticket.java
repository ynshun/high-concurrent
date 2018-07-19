package com.ynshun.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * CPU在调度线程的时候是在等待队列里随机挑选一个线程，由于这种随机性所以是无法保证线程先到先得的（synchronized控制的锁就是这种非公平锁）。
 * 但这样就会产生饥饿现象，即有些线程（优先级较低的线程）可能永远也无法获取CPU的执行权，优先级高的线程会不断的强制它的资源。那么如何解决饥饿问题呢，
 * 这就需要公平锁了。公平锁可以保证线程按照时间的先后顺序执行，避免饥饿现象的产生。但公平锁的效率比较低，因为要实现顺序执行，需要维护一个有序队列。
 * ReentrantLock便是一种公平锁，通过在构造方法中传入true就是公平锁，传入false，就是非公平锁。
 * 
 * @author Administrator
 *
 */
public class Ticket implements Runnable {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(10);

		Ticket test = new Ticket();

		for (int i = 0; i < 100; i++) {
			threadPool.submit(test);
		}
	}

	// 当前拥有的票数
	private int num = 10;
	ReentrantLock lock = new ReentrantLock();

	public void run() {
		lock.lock();
		// 输出卖票信息
		if (num > 0) {
			System.out.println(Thread.currentThread().getName() + ".....sale...." + num--);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("木有票了，明天请赶早");
		}
		lock.unlock();
	}
}
