package com.example.backtesting_engine;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public final class PerformanceReport {

    private final double totalPnl;
    private final int tradeCount;
    private final double averagePnl;
    private final double winRate;
    private final List<Double> cumPnl;

    public PerformanceReport(List<Trade> trades) {

        double pnl = 0.0;
        int count = 0;
        int winner = 0;

        List<Double> tempCumPnl = new ArrayList<>();

        for (Trade trade : trades) {
            pnl += trade.getPnl();
            tempCumPnl.add(pnl);
            count++;
            if (trade.getPnl() > 0) {
                winner++;
            }
        }

        this.totalPnl = pnl;
        this.tradeCount = count;

        if (count == 0) {
            this.averagePnl = 0.0;
            this.winRate = 0.0;
        } else {
            this.averagePnl = totalPnl / tradeCount;
            this.winRate = (double) winner / tradeCount;
        }

        // freeze cumulative PnL to enforce immutability
        this.cumPnl = Collections.unmodifiableList(tempCumPnl);
    }

    @Override
    public String toString() {
        return "PerformanceReport{" +
                "totalPnl=" + totalPnl +
                ", tradeCount=" + tradeCount +
                ", averagePnl=" + averagePnl +
                ", winRate=" + winRate +
                ", cumulativePnl=" + cumPnl +
                '}';
    }
}
