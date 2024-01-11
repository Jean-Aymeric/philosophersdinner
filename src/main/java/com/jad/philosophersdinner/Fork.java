package com.jad.philosophersdinner;

import java.util.concurrent.locks.ReentrantLock;

public class Fork {
 private final ReentrantLock lock = new ReentrantLock();

 public void lock() {
  this.lock.lock();
 }

 public void unlock() {
  this.lock.unlock();
 }
}
