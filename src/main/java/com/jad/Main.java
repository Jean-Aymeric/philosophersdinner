package com.jad;

import com.jad.philosophersdinner.Fork;
import com.jad.philosophersdinner.Philosopher;
import java.util.ArrayList;

public class Main {

  private static final int PHILOSOPHERS_COUNT = 5;
  private static final int FORKS_COUNT = 5;
  private static final String[] NAMES = {"Aristotle", "Plato", "Socrates", "Descartes", "Kant"};

  public static void main(final String[] args) {
    final ArrayList<Philosopher> philosophers = new ArrayList<>();
    final ArrayList<Fork> forks = new ArrayList<>();
    for (int i = 0; i < Main.FORKS_COUNT; i++) {
      forks.add(new Fork());
    }
    for (int i = 0; i < Main.PHILOSOPHERS_COUNT; i++) {
      final Fork leftFork = forks.get(i);
      final Fork rightFork = forks.get((i + 1) % Main.PHILOSOPHERS_COUNT);
      philosophers.add(new Philosopher(Main.NAMES[i], leftFork, rightFork));
    }

    for (final Philosopher philosopher : philosophers) {
      new Thread(philosopher).start();
    }
  }
}