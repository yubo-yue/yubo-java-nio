package org.simple.jcp.misc;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;


public class CLHLock {

	private volatile CLHNode tail;
	
	private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(CLHLock.class, CLHNode.class, "tail");
	
	public void lock(CLHNode currentThread) {
		CLHNode preNode = UPDATER.getAndSet(this, currentThread);
		if (null != preNode) {
			while (preNode.locked) {}
		}
	}
	
	public void unlock(CLHNode currentThread) {
		if (!UPDATER.compareAndSet(this, currentThread, null)) {
			currentThread.locked = false;
		}
	}
	
	
	public  static class CLHNode {
		private volatile boolean locked = true;
	}
}
