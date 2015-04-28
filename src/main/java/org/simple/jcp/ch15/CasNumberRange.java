package org.simple.jcp.ch15;

import java.util.concurrent.atomic.AtomicReference;

public class CasNumberRange {
	private static class IntPair {
		final int lower;
		final int upper;
		
		public IntPair(int lower, int upper) {
			this.lower = lower;
			this.upper = upper;
		}
	}
	
	private final AtomicReference<IntPair> values = new AtomicReference<IntPair>(new IntPair(0, 0));
	
	public int getLower() { return values.get().lower ;}
	public int getUpper() {return values.get().upper; }
	
	public void setLower(int i ) {
		while(true) {
			IntPair oldVals = values.get();
			if (i > oldVals.upper) {
				throw new IllegalArgumentException();
			}
			
			IntPair newVals = new IntPair(i, oldVals.upper);
			if (values.compareAndSet(oldVals, newVals)) {
				return;
			}
		}
	}
	
	public void setUpper(int i ) {
		while(true) {
			IntPair oldVals = values.get();
			if (i < oldVals.lower) {
				throw new IllegalArgumentException();
			}
			
			IntPair newVals = new IntPair(oldVals.lower, i);
			if (values.compareAndSet(oldVals, newVals)) {
				return;
			}
		}
	}
}
