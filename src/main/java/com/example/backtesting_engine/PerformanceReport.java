package com.example.backtesting_engine;

import java.util.List;

public final class PerformanceReport {

    private final double totalPnl;
    private final int tradeCount;

    public PerformanceReport(List<Trade> trades) {
        double pnl = 0.0;
        int count = 0;

        for (Trade trade : trades) {
            pnl += trade.getPnl();
            count++;
        }

        this.totalPnl = pnl;
        this.tradeCount = count;
    }

    public double getTotalPnl() {
        return totalPnl;
    }

    public int getTradeCount() {
        return tradeCount;
    }

    @Override
    public String toString() {
        return "PerformanceReport{" +
                "totalPnl=" + totalPnl +
                ", tradeCount=" + tradeCount +
                '}';
    }
}
