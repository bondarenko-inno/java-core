package org.example.factory;

import org.example.details.*;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Factory implements Runnable {
    private final Storage storage;
    private final Random random = new Random();
    private final AtomicBoolean isDay;
    private final CyclicBarrier barrier;

    public Factory(Storage storage, AtomicBoolean isDay, CyclicBarrier barrier) {
        this.storage = storage;
        this.isDay = isDay;
        this.barrier = barrier;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                if (isDay.get()) {
                    for (int i = 0; i < 10; i++) {
                        storage.put(createRandomDetail());
                        Thread.sleep(50);
                    }
                }
                barrier.await();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (BrokenBarrierException e) {
                break;
            }
        }
    }

    private Details createRandomDetail() {
        return switch (random.nextInt(5)) {
            case 0 -> new Head();
            case 1 -> new Hand();
            case 2 -> new Feet();
            default -> new Torso();
        };
    }
}


