package org.simple.jcp.misc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketLock {
	private AtomicInteger serviceNum = new AtomicInteger();
	private AtomicInteger ticketNum = new AtomicInteger();
	
	public int lock() {
		int myTicketNum = ticketNum.getAndIncrement();
		while(serviceNum.get() != myTicketNum) {}
		return myTicketNum;
	}
	
	public void unlock(int myTicket) {
		int next = myTicket + 1;
		serviceNum.compareAndSet(myTicket, next);
	}
	
public static void main(String[] args) throws InterruptedException {
		
		ExecutorService eService = Executors.newFixedThreadPool(4);
		TicketLock lock = new TicketLock();
		eService.execute(new Worker(lock));
		eService.execute(new Worker(lock));
		eService.execute(new Worker(lock));
		eService.execute(new Worker(lock));
		
		TimeUnit.SECONDS.sleep(40);
		
		eService.shutdown();
	}
	
	private static class Worker implements Runnable {

		private TicketLock lock;
		
		public Worker(TicketLock lock) {
			this.lock = lock;
		}
		
		@Override
		public void run() {
			int myTicket = lock.lock();
			System.out.println(Thread.currentThread() + " is running with " + myTicket);
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock(myTicket);
		}
		
	}
}
