package com.github.sbst.core.performance;

import java.util.concurrent.atomic.AtomicLong;

public class GlobalIterationsCounter {
    private static long atomicLong = 0;
    private static final GlobalIterationsCounter instance = new GlobalIterationsCounter();

    private GlobalIterationsCounter() {}

    public static GlobalIterationsCounter getInstance() {
        return instance;
    }

    public void contributeEvent() {
        atomicLong = atomicLong + 1;
    }

    public long getEventCount() {
        return atomicLong;
    }
}
