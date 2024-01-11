package com.jad.philosophersdinner;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Forks extends ArrayList<Fork> {

  private final Semaphore semaphore;

  public Forks(final int forksCount) {
    this.semaphore = new Semaphore(forksCount / 2);
    for (int i = 0; i < forksCount; i++) {
      this.add(new Fork());
    }
  }

  public void acquire() throws InterruptedException {
    this.semaphore.acquire();
  }

  public void release() {
    this.semaphore.release();
  }

  public boolean tryAcquire() {
    return this.semaphore.tryAcquire();
  }
}
