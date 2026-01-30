package com.example.backtesting_engine;

public class Engine {

    DataFeed dataFeed;
    Strategy strategy;

    public Engine(DataFeed dataFeed, Strategy strategy){
        this.dataFeed = dataFeed;
        this.strategy = strategy;
    }

    public void callStrategy(){
        while(dataFeed.hasNext()){
            MarketSnapshot currsnapshot = dataFeed.snapshot();
            int intent = strategy.showIntent(currsnapshot);
            dataFeed.forward();
        }

    }
}
