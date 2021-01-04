package com.bigdistributor.core.blockmanagement.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Threads {
    public static int numThreads() {
        return Math.max(1, getThreads());
    }

    public static ExecutorService createExService() {
        return createExService(numThreads());
    }

    public static ExecutorService createExService(final int numThreads) {
        return Executors.newFixedThreadPool(numThreads);
    }

    public static int getThreads() {
        int processors = Runtime.getRuntime().availableProcessors();
        int threads = processors;
        return threads;
    }
}
