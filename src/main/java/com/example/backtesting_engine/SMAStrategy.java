package com.example.backtesting_engine;

public class SMAStrategy implements Strategy {

    private final RollingSMA shortSMA = new RollingSMA(20);
    private final RollingSMA longSMA  = new RollingSMA(50);

    // -1 = unknown, 0 = flat, 1 = long
    private int previousSignal = -1;

    public Integer showIntent(MarketSnapshot snapshot) {
        int price = snapshot.getPrice();

        shortSMA.addPrice(price);
        longSMA.addPrice(price);

        // Warm-up period
        if (!shortSMA.isReady() || !longSMA.isReady()) {
            return null; // no intent
        }

        double shortAvg = shortSMA.getAverage();
        double longAvg  = longSMA.getAverage();

        int currentSignal = shortAvg > longAvg ? 1 : 0;

        // Edge-triggered: emit intent only on change
        if (currentSignal != previousSignal) {
            previousSignal = currentSignal;
            return currentSignal; // new intent
        }

        return null; // no change
    }
}
