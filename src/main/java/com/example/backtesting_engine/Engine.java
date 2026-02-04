package com.example.backtesting_engine;

public class Engine {

    private final DataFeed dataFeed;
    private final Strategy strategy;

    private Intent currentPosition = null;
    private double entryPrice = 0.0;

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
        System.out.println("ENTER LONG at " + price);
    }

    private void exitLong(double price) {
        currentPosition = Intent.FLAT;
        double pnl = price - entryPrice;
        entryPrice = 0.0;
        System.out.println("EXIT LONG at " + price + " | PnL = " + pnl);
    }
}
