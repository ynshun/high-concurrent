package com.ynshun.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * 3)读写锁
 * 
 * 读写锁将对一个资源（比如文件）的访问分成了2个锁，一个读锁和一个写锁。
 * 
 * 正因为有了读写锁，才使得多个线程之间的读操作可以并发进行，不需要同步，而写操作需要同步进行，提高了效率。
 * 
 * ReadWriteLock就是读写锁，它是一个接口，ReentrantReadWriteLock实现了这个接口。
 * 
 * 可以通过readLock()获取读锁，通过writeLock()获取写锁。
 * 
 * @author Administrator
 *
 */
public class ReentrantReadWriteLockTest implements Runnable {
	private static int i = 0;

	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	ReadLock rl = lock.readLock();
	WriteLock wl = lock.writeLock();

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(2);

		ReentrantReadWriteLockTest test = new ReentrantReadWriteLockTest();

		for (int i = 0; i < 10; i++) {
			threadPool.submit(test);
		}
	}

	public void run() {
		try {
			// rl.lock();
			System.out.println(Thread.currentThread().getName() + "可以开始读数据了....." + i);
			// rl.unlock();
			
			wl.lock();
			i++;
			System.out.println(Thread.currentThread().getName() + "写数据完成....." + i);
		} finally {
			wl.unlock();
		}
	}
}
