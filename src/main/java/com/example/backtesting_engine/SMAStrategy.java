package com.example.backtesting_engine;

public class SMAStrategy implements Strategy {

    private final RollingSMA shortSMA = new RollingSMA(20);
    private final RollingSMA longSMA  = new RollingSMA(50);

    private int previousSignal = -1;

    public Integer showIntent(MarketSnapshot snapshot) {
        int price = snapshot.getPrice();

        shortSMA.addPrice(price);
        longSMA.addPrice(price);

        if (!shortSMA.isReady() || !longSMA.isReady()) {
            return null;
        }

        double shortAvg = shortSMA.getAverage();
        double longAvg  = longSMA.getAverage();

        int currentSignal = shortAvg > longAvg ? 1 : 0;

        if (currentSignal != previousSignal) {
            previousSignal = currentSignal;
            return currentSignal;
        }

        return null;
    }
}
