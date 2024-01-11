package com.jad.philosophersdinner;

public class Philosopher implements Runnable {

  private static final long EATING_TIME = 100;
  private static final long MAX_HUNGRY_TIME = 1000;
  private static final long MAX_THINKING_TIME = 900;
  private final Fork leftFork;
  private final Fork rightFork;
  private final String name;
  private long lastEatTime;
  private PhilosopherState state;

  public Philosopher(final String name, final Fork leftFork, final Fork rightFork) {
    this.name = name;
    this.leftFork = leftFork;
    this.rightFork = rightFork;
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

  private void takeForks() {
    this.state = PhilosopherState.HUNGRY;
    System.out.println(this.name + " is hungry");

    this.rightFork.lock();
    System.out.println(this.name + " took right fork");
    this.leftFork.lock();
    System.out.println(this.name + " took left fork");

    if ((System.currentTimeMillis() - this.lastEatTime) >= Philosopher.MAX_HUNGRY_TIME) {
      this.state = PhilosopherState.DEAD;
      System.out.println(this.name + " is dead");
      System.exit(0);
    }
  }

  private void eat() throws InterruptedException {
    this.state = PhilosopherState.EATING;
    System.out.println(this.name + " is eating");
    Thread.sleep(Philosopher.EATING_TIME);
    this.rightFork.unlock();
    System.out.println(this.name + " released right fork");
    this.leftFork.unlock();
    System.out.println(this.name + " released left fork");
    this.lastEatTime = System.currentTimeMillis();
  }
}
