package com.example.backtesting_engine;

public class Portfolio {

    private double entryprice;
    private int quantity;
    private double cash;

    public Portfolio(){
        this.cash = 10000.0;
    }

    public ExecutionType attemptEnterLong(double price, double percentage){
        double cashForTrade = (percentage*cash)/100.0;
        quantity = (int)Math.floor(cashForTrade/price);
        if(quantity>0){
            cash -= (quantity*price);
            this.entryprice = price;
            return ExecutionType.ENTRY;
        }
        else{
            return null;
        }
    }

    public ExecutionType attemptExitLong(double price){
        cash  += quantity*price;
        if(quantity==0){
            return null;
        }
        quantity = 0;
        return ExecutionType.EXIT;
    }

}
