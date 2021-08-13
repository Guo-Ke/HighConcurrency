package com.example.concurrency;

import com.example.concurrency.annocations.NoThreadSafe;
import com.example.concurrency.annocations.ThreadSafe;
import com.example.concurrency.po.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author 郑启
 * @date 2020/07/21 15:32
 */
@Slf4j
class ConcurrencyTest {
	public int clientTotal = 5000;
	public int threadTotal = 200;
	public int count = 0;

	public void add(){
		count++;
	}

	@Test
	@NoThreadSafe
	void aaa() throws InterruptedException {
		int count = 0;
		ExecutorService service = Executors.newCachedThreadPool();
		Semaphore semaphore = new Semaphore(threadTotal);
		CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
		for (int i = 0; i < clientTotal; i++) {
			service.execute(() -> {
				try {
					semaphore.acquire();
					add();
					semaphore.release();
				}catch (Exception e){
					log.error("异常：", e);
				}
				countDownLatch.countDown();
			});
		}
		//计数为0时放开。countDownLatch的作用是：线程全部执行完才会继续往下执行，否则线程全部创建完就往下执行
		countDownLatch.await();
		service.shutdown();
		log.info("执行完成{}个", count);
	}

	@Test
	@ThreadSafe
	void bbb() throws InterruptedException {
		AtomicInteger count1 = new AtomicInteger(0);
		ExecutorService service = Executors.newCachedThreadPool();
		Semaphore semaphore = new Semaphore(threadTotal);
		CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
		for (int i = 0; i < clientTotal; i++) {
			service.execute(() -> {
				try {
					semaphore.acquire();
					count1.incrementAndGet();
//					count1.compareAndSet()
					semaphore.release();
				}catch (Exception e){
					log.error("异常：", e);
				}
				countDownLatch.countDown();
			});
		}
		//计数为0时放开。countDownLatch的作用是：线程全部执行完才会继续往下执行，否则线程全部创建完就往下执行
		countDownLatch.await();
		service.shutdown();
		log.info("执行完成{}个", count1);
	}

	@Test
	void ccc(){
		User user = new User();
		User user1 = new User();
		AtomicReference<User> atomicUser = new AtomicReference<>(user);
		atomicUser.compareAndSet(user, user1);

		User user2 = atomicUser.get();
		System.out.println(user2 == user);
		System.out.println(user2 == user1);
	}


	@Test
	void ddd(){
		User user = new User();
		user.usnm("周明辉");
		user.age(30);
//		AtomicIntegerFieldUpdater<User> usnmUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "usnm");
//		updater.compareAndSet(user, "周明辉", "马楠");
		//必须是用public volatile修饰的int类型（不能是Integer）的非静态字段
		AtomicIntegerFieldUpdater<User> ageUpdater = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
		ageUpdater.compareAndSet(user, 30, 31);
		System.out.println(user.age());
	}

	@Test
	@ThreadSafe
	void eee() throws InterruptedException {
		ExecutorService service = Executors.newCachedThreadPool();
		Semaphore semaphore = new Semaphore(threadTotal);
		CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
		for (int i = 0; i < clientTotal; i++) {
			service.execute(() -> {
				try {
					semaphore.acquire();
					add1();
					semaphore.release();
				}catch (Exception e){
					log.error("异常：", e);
				}
				countDownLatch.countDown();
			});
		}
		//计数为0时放开。countDownLatch的作用是：线程全部执行完才会继续往下执行，否则线程全部创建完就往下执行
		countDownLatch.await();
		service.shutdown();
		log.info("执行完成{}个", count);
	}

	public synchronized void add1(){
		count++;
	}
}
