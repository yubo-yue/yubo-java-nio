package org.simple.jcp.misc;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;


public class MCSLock {
	public static class MCSNode {
		volatile MCSNode next;
		volatile boolean isBlock = true;
	}
	
	volatile MCSNode tail;
	
	private static final AtomicReferenceFieldUpdater<MCSLock, MCSNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(MCSLock.class, MCSNode.class, "tail");
	
	public void lock(MCSNode currentThread) {
		MCSNode predecessor = UPDATER.getAndSet(this, currentThread); //set the tail to currentThread node
		if (null != predecessor) {
			predecessor.next = currentThread;
			
			while(currentThread.isBlock) {}
		}
	}
	
	public void unlock(MCSNode currentThread) {
		if (currentThread.isBlock) {
			return;
		}
		
		if (currentThread.next == null) {
			if (UPDATER.compareAndSet(this, currentThread, null)) { //currentThread is the last node
				return ;
			} else {
				while (currentThread.next == null) {}
			}
		}
		
		currentThread.next.isBlock = false;
		currentThread.next = null; //for GC
	}
	
}
