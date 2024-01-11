package com.jad;

import com.jad.philosophersdinner.Forks;
import com.jad.philosophersdinner.Philosopher;
import java.util.ArrayList;

public class Main {

  private static final int PHILOSOPHERS_COUNT = 5;
  private static final int FORKS_COUNT = 5;
  private static final String[] NAMES = {"Aristotle", "Plato", "Socrates", "Descartes", "Kant"};

  public static void main(final String[] args) {
    final ArrayList<Philosopher> philosophers = new ArrayList<>();
    final Forks forks = new Forks(Main.FORKS_COUNT);

    for (int i = 0; i < Main.PHILOSOPHERS_COUNT; i++) {
      philosophers.add(new Philosopher(Main.NAMES[i], forks));
    }

    for (final Philosopher philosopher : philosophers) {
      philosopher.start();
    }
  }
}