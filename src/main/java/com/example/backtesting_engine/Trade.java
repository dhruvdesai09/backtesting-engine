package com.example.backtesting_engine;

import lombok.Getter;

@Getter
public class Trade {

    private final Intent direction;
    private final double entryPrice;
    private final double exitPrice;
    private final double pnl;

    public Trade(Intent direction, double entryPrice, double exitPrice) {
        this.direction = direction;
        this.entryPrice = entryPrice;
        this.exitPrice = exitPrice;
        this.pnl = exitPrice - entryPrice;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "direction=" + direction +
                ", entryPrice=" + entryPrice +
                ", exitPrice=" + exitPrice +
                ", pnl=" + pnl +
                '}';
    }
}
