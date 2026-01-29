package com.example.backtesting_engine;

public class Engine {

    DataFeed dataFeed;
    public Engine(DataFeed dataFeed){
        this.dataFeed = dataFeed;
    }

    public void callStrategy(){
        while(dataFeed.hasNext()){
            MarketSnapshot currsnapshot = dataFeed.snapshot();
            int price = currsnapshot.getPrice();
            //send the current price to the strategy
            dataFeed.forward();
        }

    }
}
