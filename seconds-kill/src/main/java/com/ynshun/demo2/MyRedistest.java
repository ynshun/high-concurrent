package com.ynshun.demo2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import redis.clients.jedis.Jedis;

public class MyRedistest {
	public final static int number = 50;
	public static int i = 0;

	public static void main(String[] args) throws Exception {
		final String watchkeys = "watchkeys";
		ExecutorService executor = Executors.newFixedThreadPool(20); // 20个线程池并发数

		final Jedis jedis = new Jedis("192.168.72.129", 6379);
		jedis.auth("123456");

		jedis.set(watchkeys, number + "");// 设置起始的抢购数
		jedis.close();

		for (int i = 0; i < 1000; i++) {// 设置1000个人来发起抢购
			executor.execute(new MyRunnable());
		}
		executor.shutdown();

		System.out.println(i);
	}

}