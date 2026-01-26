package com.example.backtesting_engine;

import lombok.Getter;

@Getter
public final class MarketSnapshot {
    private final int price;

    public MarketSnapshot(int price){
        this.price = price;
    }

}
