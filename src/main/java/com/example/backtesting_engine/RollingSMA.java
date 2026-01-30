package com.example.backtesting_engine;

import java.util.ArrayDeque;
import java.util.Deque;

public class RollingSMA {

    private final int windowSize;
    private final Deque<Integer> window = new ArrayDeque<>();
    private double sum = 0.0;

    public RollingSMA(int windowSize) {
        this.windowSize = windowSize;
    }

    public void addPrice(int price) {
        window.addLast(price);
        sum += price;

        if (window.size() > windowSize) {
            sum -= window.removeFirst();
        }
    }

    public boolean isReady() {
        return window.size() == windowSize;
    }

    public double getAverage() {
        if (!isReady()) {
            throw new IllegalStateException("SMA not ready");
        }
        return sum / windowSize;
    }
}
