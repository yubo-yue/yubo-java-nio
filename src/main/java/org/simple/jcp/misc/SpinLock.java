package org.simple.jcp.misc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class SpinLock {
	private AtomicReference<Thread> owner = new AtomicReference<Thread>();
	
	public void lock() {
		Thread currentThread = Thread.currentThread();
		while(!owner.compareAndSet(null, currentThread)) {
			System.out.println(currentThread  + "is waiting");
		}
		System.out.println(currentThread + " get the lock");
	}
	
	public void unlock() {
		Thread currentThread = Thread.currentThread();
		owner.compareAndSet(currentThread, null);
		System.out.println(currentThread + " release the lock");
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		ExecutorService eService = Executors.newFixedThreadPool(2);
		SpinLock lock = new SpinLock();
		eService.execute(new Worker(lock));
		eService.execute(new Worker(lock));
		
		TimeUnit.SECONDS.sleep(40);
		
		eService.shutdown();
	}
	
	private static class Worker implements Runnable {

		private SpinLock lock;
		
		public Worker(SpinLock lock) {
			this.lock = lock;
		}
		
		@Override
		public void run() {
			lock.lock();
			System.out.println(Thread.currentThread() + " is running");
			try {
				TimeUnit.SECONDS.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock();
		}
		
	}
}
