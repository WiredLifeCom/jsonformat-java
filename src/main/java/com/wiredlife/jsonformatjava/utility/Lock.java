package com.wiredlife.jsonformatjava.utility;

public class Lock {

	private boolean isLocked = false;

	public synchronized void lock() throws InterruptedException {
		while (this.isLocked) {
			wait();
		}
		this.isLocked = true;
	}

	public synchronized void unlock() {
		this.isLocked = false;
		notify();
	}

}
