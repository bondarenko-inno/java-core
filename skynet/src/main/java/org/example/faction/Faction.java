package org.example.faction;

import org.example.details.*;
import org.example.factory.Storage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Faction implements Runnable {
    private final String name;
    private final Storage storage;
    private final Map<Class<? extends Details>, Integer> parts = new HashMap<>();
    private int robots = 0;
    private final AtomicBoolean isDay;
    private final CyclicBarrier barrier;

    public Faction(String name, Storage storage, AtomicBoolean isDay, CyclicBarrier barrier) {
        this.name = name;
        this.storage = storage;
        this.isDay = isDay;
        this.barrier = barrier;

        parts.put(Head.class, 0);
        parts.put(Torso.class, 0);
        parts.put(Hand.class, 0);
        parts.put(Feet.class, 0);
    }

    @Override
    public void run() {
        for (int day = 1; day <= 100; day++) {
            try {
                if (!isDay.get()) {
                    for (int i = 0; i < 5; i++) {
                        Details d = storage.take();
                        parts.merge(d.getClass(), 1, Integer::sum);
                    }
                    buildRobots();
                    System.out.printf("[%s] Day %d: robots %d\n", name, day, robots);
                }

                barrier.await();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (BrokenBarrierException e) {
                break;
            }
        }
    }

    private void buildRobots() {
        while (parts.get(Head.class) >= 1 &&
                parts.get(Torso.class) >= 1 &&
                parts.get(Hand.class) >= 2 &&
                parts.get(Feet.class) >= 2) {

            parts.put(Head.class, parts.get(Head.class) - 1);
            parts.put(Torso.class, parts.get(Torso.class) - 1);
            parts.put(Hand.class, parts.get(Hand.class) - 2);
            parts.put(Feet.class, parts.get(Feet.class) - 2);
            robots++;
        }
    }

    public int getRobotCount() {
        return robots;
    }

}


