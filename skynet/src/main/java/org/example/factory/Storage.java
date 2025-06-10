package org.example.factory;

import org.example.details.Details;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {
    protected static final int MAX_CAPACITY = 10;
    private final BlockingQueue<Details> queue = new LinkedBlockingQueue<>(MAX_CAPACITY);

    private final AtomicInteger putCount = new AtomicInteger(0);
    private final AtomicInteger takeCount = new AtomicInteger(0);


    public void put(Details detail) throws InterruptedException {
        queue.put(detail);
        putCount.incrementAndGet();
    }

    public Details take() throws InterruptedException {
        takeCount.incrementAndGet();
        return queue.take();
    }

    public int size() {
        return queue.size();
    }

    public int getPutCount() {
        return putCount.get();
    }

    public int getTakeCount() {
        return takeCount.get();
    }

    public int getCurrentSize() {
        return queue.size();
    }

    public boolean isConsistent() {
        return getPutCount() == getTakeCount() + getCurrentSize();
    }
}


