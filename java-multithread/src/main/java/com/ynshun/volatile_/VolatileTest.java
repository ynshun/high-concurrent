package com.ynshun.volatile_;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Java提供了volatile关键字来保证可见性
 * 
 * //线程1执行的代码 int i = 0; i = 10;
 * 
 * //线程2执行的代码 j = i;
 * 
 * 由上面的分析可知，当线程1执行 i
 * =10这句时，会先把i的初始值加载到工作内存中，然后赋值为10，那么在线程1的工作内存当中i的值变为10了，却没有立即写入到主存当中。
 * 
 * 此时线程2执行 j = i，它会先去主存读取i的值并加载到线程2的工作内存当中，注意此时内存当中i的值还是0，那么就会使得j的值为0，而不是10.
 * 
 * 这就是可见性问题，线程1对变量i修改了之后，线程2没有立即看到线程1修改的值。
 * 
 * 
 * @author Administrator
 *
 */
public class VolatileTest {
	private volatile static int r = 10;

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newFixedThreadPool(100);

		for (int i = 0; i < 200; i++) {

			threadPool.submit(new Runnable() {
				@Override
				public void run() {
					int j = r;
					if (j > 0) {
						r--;
						System.err.println("还剩下：" + r);
					} else {
						System.out.println("真不好意思，没有了！！");
					}
				}
			});
		}
	}

}
