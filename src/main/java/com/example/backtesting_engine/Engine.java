package com.example.backtesting_engine;

public class Engine {

    private final DataFeed dataFeed;
    private final Strategy strategy;

    private int currentPosition = 0;
    private double entryPrice = 0.0;

    public Engine(DataFeed dataFeed, Strategy strategy) {
        this.dataFeed = dataFeed;
        this.strategy = strategy;
    }

    public void run() {
        while (dataFeed.hasNext()) {

            MarketSnapshot snapshot = dataFeed.snapshot();
            double price = snapshot.getPrice();

            Integer intent = strategy.showIntent(snapshot);

            if (intent != null) {
                handleIntent(intent, price);
            }

            dataFeed.forward();
        }
    }

    private void handleIntent(int intent, double price) {

        if (intent == 1 && currentPosition == 0) {
            enterLong(price);
        }

        else if (intent == 0 && currentPosition == 1) {
            exitLong(price);
        }

    }

    private void enterLong(double price) {
        currentPosition = 1;
        entryPrice = price;
        System.out.println("ENTER LONG at " + price);
    }

    private void exitLong(double price) {
        currentPosition = 0;
        double pnl = price - entryPrice;
        entryPrice = 0.0;
        System.out.println("EXIT LONG at " + price + " | PnL = " + pnl);
    }
}
