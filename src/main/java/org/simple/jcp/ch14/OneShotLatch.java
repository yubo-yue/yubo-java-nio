package org.simple.jcp.ch14;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class OneShotLatch {
	
	private final Sync sync = new Sync();
	
	public void signal()  {
		sync.releaseShared(0);
	}
	
	public void await() throws InterruptedException {
		sync.acquireSharedInterruptibly(0);
	}
	
	private class Sync  extends AbstractQueuedSynchronizer {
		protected int tryAcquireShared(int ignored) {
			return (getState() == 1)  ? 1 : -1;
		}
		
		protected boolean tryReleaseShared(int ignored) {
			setState(1);
			return true;
		}
	}
	
	public static void main(String [] args) {
		ExecutorService eService = Executors.newFixedThreadPool(5);
		OneShotLatch latch = new OneShotLatch();
		for (int i = 0; i < 10; ++i ) {
			eService.execute(new LocalTask(latch));
		}
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		latch.signal();
	}
	
	private static class LocalTask implements Runnable {
		OneShotLatch latch ;
		public LocalTask(OneShotLatch latch) {
			this.latch = latch;
		}
		
		@Override
		public void run() {
			System.out.println("start to run");
			System.out.println("waiting for latch to open");
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("latch is opened");
			try {
				TimeUnit.SECONDS.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("task running and completed");
		}
		
	}
}
