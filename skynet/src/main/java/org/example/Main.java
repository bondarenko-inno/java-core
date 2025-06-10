package org.example;

import org.example.faction.Faction;
import org.example.factory.Factory;
import org.example.factory.Storage;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Storage storage = new Storage();

        AtomicBoolean isDay = new AtomicBoolean(true);
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            isDay.set(!isDay.get());
            System.out.println("\n=== Phase switch: now it's " + (isDay.get() ? "DAY" : "NIGHT") + " ===\n");
        });

        Factory factory = new Factory(storage, isDay, barrier);
        Faction world = new Faction("World", storage, isDay, barrier);
        Faction wednesday = new Faction("Wednesday", storage, isDay, barrier);

        Thread factoryThread = new Thread(factory);
        Thread worldThread = new Thread(world);
        Thread wendnesdayThread = new Thread(wednesday);

        factoryThread.start();
        worldThread.start();
        wendnesdayThread.start();

        worldThread.join();
        wendnesdayThread.join();
        factoryThread.interrupt();

        System.out.println("Results:");
        System.out.printf("World: %d robots\n", world.getRobotCount());
        System.out.printf("Wednesday: %d robots\n", wednesday.getRobotCount());

        System.out.println("\nSynchronization statistics:");
        System.out.println("putCount = " + storage.getPutCount());
        System.out.println("takeCount = " + storage.getTakeCount());
        System.out.println("queue size = " + storage.getCurrentSize());
        System.out.println("Consistency check: "
                + (storage.isConsistent() ? "✅ OK" : "❌ Error"));

    }
}


