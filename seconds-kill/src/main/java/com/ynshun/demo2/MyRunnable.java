package com.ynshun.demo2;

import redis.clients.jedis.Jedis;

public class MyRunnable implements Runnable {

	String watchkeys = "watchkeys";// 库存keys
	String lockkeys = "seckill";
	Jedis jedis = new Jedis("192.168.72.129", 6379);
	
	public MyRunnable() {
		jedis.auth("123456");
	}


	@Override
	public void run() {
		try {
			String val = jedis.get(watchkeys);
			int valint = Integer.valueOf(val);
			
			if (valint < 1) {
				// String failinfo = "商品已经被抢光了，抢购失败";
				// System.out.println(failinfo);
				return;
			}
			
			if (jedis.exists(lockkeys)) { // 判断当前是否能获取到锁
				// System.err.println("被锁，重新试中.......");
				run();
			}

			try {
				jedis.set(lockkeys, ""); // 加锁
				
				jedis.incrBy("watchkeys", -1); // 库存减1  （必须用incrBy原子操作不能使用jedis.set("watchkeys", (valint - 1) + "") ）
				
				// System.err.println("抢购成功");
				
				jedis.del(lockkeys); // 解锁
				
				jedis.close();
				
				MyRedistest.i++;
			} catch (Exception e) {
				e.printStackTrace();
				
				jedis.del(lockkeys); // 解锁
				run(); // 重新尝试秒杀
			}
		} catch(Exception e) {
			
		}
	}
			
			

}