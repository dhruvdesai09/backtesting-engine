package com.example.backtesting_engine;

public class DataFeed {

    private int[] historicalData;
    private int currindex = 0;

    DataFeed(int[] historicalData){
        if(historicalData.length==0 || historicalData == null){
            throw new IllegalArgumentException("Data cannot be empty");
        }
        this.historicalData = historicalData;
    }

    public boolean hasNext(){
        return currindex< historicalData.length-1;
    }

    public void forward(){
        if (!hasNext()){
            throw new IllegalStateException("Reached the end");
        }
        currindex++;
    }

    public MarketSnapshot snapshot(){
        return new MarketSnapshot(historicalData[currindex]);
    }
}
