package com.example.backtesting_engine;

import java.util.ArrayList;

public class Engine {

    private final DataFeed dataFeed;
    private final Strategy strategy;

    private Intent currentPosition = null;
    private double entryPrice = 0.0;
    private final ArrayList<Trade> trades = new ArrayList<>();

    public Engine(DataFeed dataFeed, Strategy strategy) {
        this.dataFeed = dataFeed;
        this.strategy = strategy;
    }

    public void run() {
        while (dataFeed.hasNext()) {

            MarketSnapshot snapshot = dataFeed.snapshot();
            double price = snapshot.getPrice();

            Intent intent = strategy.showIntent(snapshot);

            if (intent != null) {
                handleIntent(intent, price);
            }

            dataFeed.forward();
        }
    }

    private void handleIntent(Intent intent, double price) {

        if (intent == Intent.LONG && currentPosition == Intent.FLAT) {
            enterLong(price);
        }
        else if (intent == Intent.FLAT && currentPosition == Intent.LONG) {
            exitLong(price);
        }

    }

    private void enterLong(double price) {
        currentPosition = Intent.LONG;
        entryPrice = price;

    }

    private void exitLong(double price) {
        currentPosition = Intent.FLAT;
        trades.add(new Trade(Intent.LONG, entryPrice, price));
        entryPrice = 0.0;
    }
}
