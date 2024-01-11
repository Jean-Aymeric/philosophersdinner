package com.jad.philosophersdinner;

public class Philosopher extends Thread {

  private static final long EATING_TIME = 100;
  private static final long MAX_HUNGRY_TIME = 700;
  private static final long MAX_THINKING_TIME = 400;
  private static final long TIME_BETWEEN_TAKING_FORKS = 100;
  private final Forks forks;
  private final String name;
  private long lastEatTime;
  private PhilosopherState state;

  public Philosopher(final String name, final Forks forks) {
    this.name = name;
    this.forks = forks;
  }

  @Override
  public void run() {
    this.lastEatTime = System.currentTimeMillis();
    for (; ; ) {
      try {
        this.think();
        this.takeForks();
        this.eat();
      } catch (final InterruptedException exception) {
        throw new RuntimeException(exception);
      }
    }
  }

  private void think() throws InterruptedException {
    this.state = PhilosopherState.THINKING;
    System.out.println(this.name + " is thinking");
    Thread.sleep((long) (Math.random() * Philosopher.MAX_THINKING_TIME));
  }

  private void takeForks() throws InterruptedException {
    this.state = PhilosopherState.HUNGRY;
    System.out.println(this.name + " is hungry");
    do {
      final int newPriority = this.calculatePriority();
      if (newPriority != this.getPriority()) {
        System.out.println(this.name + " changed priority to " + newPriority);
        this.setPriority(newPriority);
      }
    } while (!this.forks.tryAcquire());

    this.takeRightFork();
    Thread.sleep(Philosopher.TIME_BETWEEN_TAKING_FORKS);
    this.takeLeftFork();
  }

  private void eat() throws InterruptedException {
    if ((System.currentTimeMillis() - this.lastEatTime) >= Philosopher.MAX_HUNGRY_TIME) {
      this.state = PhilosopherState.DEAD;
      System.out.println(this.name + " is dead");
      System.exit(0);
    }
    this.state = PhilosopherState.EATING;
    System.out.println(this.name + " is eating");
    Thread.sleep(Philosopher.EATING_TIME);
    System.out.println(this.name + " released right fork");
    System.out.println(this.name + " released left fork");
    this.forks.release();
    this.setPriority(Thread.MIN_PRIORITY);
    System.out.println(this.name + " changed priority to " + Thread.MIN_PRIORITY);
    this.lastEatTime = System.currentTimeMillis();
  }

  private int calculatePriority() {
    int priority = (int) (
        ((System.currentTimeMillis() - this.lastEatTime) * Thread.MAX_PRIORITY) / Philosopher.MAX_HUNGRY_TIME);
    priority = Math.max(Math.min(priority, Thread.MAX_PRIORITY), Thread.MIN_PRIORITY);
    //System.out.println(this.name + " calculated priority to " + priority);
    return priority;
  }

  private void takeRightFork() throws InterruptedException {
    System.out.println(this.name + " took right fork");
  }

  private void takeLeftFork() throws InterruptedException {
    System.out.println(this.name + " took left fork");
  }
}
