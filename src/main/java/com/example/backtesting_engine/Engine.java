package com.example.backtesting_engine;

import java.util.ArrayList;
import java.util.List;

public class Engine {

    private final DataFeed dataFeed;
    private final Strategy strategy;

    private Intent currentPosition = Intent.FLAT;
    private Intent pendingIntent = null;

    private double entryPrice = 0.0;
    private final List<Trade> trades = new ArrayList<>();

    public Engine(DataFeed dataFeed, Strategy strategy) {
        this.dataFeed = dataFeed;
        this.strategy = strategy;
    }

    public void run() {
        while (dataFeed.hasNext()) {

            MarketSnapshot snapshot = dataFeed.snapshot();
            double price = snapshot.getPrice();

            if (pendingIntent != null) {
                executePendingIntent(price);
                pendingIntent = null;
            }

            Intent intent = strategy.showIntent(snapshot);

            if (intent != null) {
                handleIntent(intent);
            }

            dataFeed.forward();
        }
    }

    private void handleIntent(Intent intent) {
        if (intent == Intent.LONG && currentPosition == Intent.FLAT) {
            pendingIntent = Intent.LONG;
        } else if (intent == Intent.FLAT && currentPosition == Intent.LONG) {
            pendingIntent = Intent.FLAT;
        }
    }

    private void executePendingIntent(double price) {
        if (pendingIntent == Intent.LONG) {
            enterLong(price);
        } else if (pendingIntent == Intent.FLAT) {
            exitLong(price);
        }
    }

    private void enterLong(double price) {
        double executionPrice = applyTransactionCost(price, ExecutionType.ENTRY);
        currentPosition = Intent.LONG;
        entryPrice = executionPrice;
    }

    private void exitLong(double price) {
        double executionPrice = applyTransactionCost(price, ExecutionType.EXIT);
        currentPosition = Intent.FLAT;
        trades.add(new Trade(Intent.LONG, entryPrice, executionPrice));
        entryPrice = 0.0;
    }

    private double applyTransactionCost(double price, ExecutionType type) {
        double cost = 1.0;

        if (type == ExecutionType.ENTRY) {
            return price + cost;
        } else {
            return price - cost;
        }
    }

    public List<Trade> getTrades() {
        return trades;
    }
}
