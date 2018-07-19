package com.ynshun.demo1;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class MyRunnable implements Runnable {

	String watchkeys = "watchkeys";// 库存keys
	Jedis jedis = new Jedis("192.168.72.129", 6379);

	String userinfo;

	public MyRunnable() {
		this(null);
	}

	public MyRunnable(String uinfo) {
		this.userinfo = uinfo;

		jedis.auth("123456");
	}

	@Override
	public void run() {
		try {
			jedis.watch(watchkeys);// watchkeys

			String val = jedis.get(watchkeys);
			int valint = Integer.valueOf(val);

			if (valint <= 100 && valint >= 1) {

				Transaction tx = jedis.multi();// 开启事务
				// 2-5.处理业务逻辑，如将数据放置在一个mq中，然后通过incr、incrby、decr、decrby原子操作命令控制库存数，减少数据库IO的开销
				tx.incrBy("watchkeys", -1); // 必须使用原子操作（不能使用set方法），不然会多个进程都拿到锁

				List<Object> list = tx.exec();// 提交事务，如果此时watchkeys被改动了，则返回null

				if (list == null || list.size() == 0) {

					String failuserifo = "fail" + userinfo;
					String failinfo = "用户：" + failuserifo + "商品争抢失败，抢购失败";
					System.out.println(failinfo);
					/* 抢购失败业务逻辑 */
					// jedis.setnx(failuserifo, failinfo);
				} else {
					for (Object succ : list) {
						String succuserifo = "succ" + succ.toString() + userinfo;
						String succinfo = "用户：" + succuserifo + "抢购成功，当前抢购成功人数:" + (1 - (valint - MyRedistest.number));
						System.err.println(succinfo);
						/* 抢购成功业务逻辑 */
						// jedis.setnx(succuserifo, succinfo);
					}
				}
			} else {
				String failuserifo = "kcfail" + userinfo;
				String failinfo1 = "用户：" + failuserifo + "商品被抢购完毕，抢购失败";
				System.out.println(failinfo1);
				// jedis.setnx(failuserifo, failinfo1);
				// Thread.sleep(500);
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jedis.close();
		}
	}

}