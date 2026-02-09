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
    private final List<Double> cumulativePnl;
    private final double maxDrawDown;
    private final double profitFactor;

    public PerformanceReport(List<Trade> trades) {

        double total = 0.0;
        int count = 0;
        int winners = 0;
        double grossProfit = 0.0;
        double grossLoss = 0.0;

        List<Double> tempCumulative = new ArrayList<>();

        for (Trade trade : trades) {
            double tradePnl = trade.getPnl();
            total += tradePnl;
            tempCumulative.add(total);
            count++;

            if (tradePnl > 0) {
                winners++;
                grossProfit += tradePnl;
            } else if (tradePnl < 0) {
                grossLoss += Math.abs(tradePnl);
            }
        }

        this.totalPnl = total;
        this.tradeCount = count;

        if (count == 0) {
            this.averagePnl = 0.0;
            this.winRate = 0.0;
            this.cumulativePnl = Collections.emptyList();
            this.maxDrawDown = 0.0;
            this.profitFactor = 0.0;
            return;
        }

        this.averagePnl = totalPnl / tradeCount;
        this.winRate = (double) winners / tradeCount;
        this.cumulativePnl = Collections.unmodifiableList(tempCumulative);

        double peak = cumulativePnl.get(0);
        double mdd = 0.0;

        for (double value : cumulativePnl) {
            if (value > peak) {
                peak = value;
            } else {
                double drawdown = peak - value;
                if (drawdown > mdd) {
                    mdd = drawdown;
                }
            }
        }

        this.maxDrawDown = mdd;
        this.profitFactor = grossLoss == 0.0 ? Double.POSITIVE_INFINITY : grossProfit / grossLoss;
    }

    @Override
    public String toString() {
        return "PerformanceReport{" +
                "totalPnl=" + totalPnl +
                ", tradeCount=" + tradeCount +
                ", averagePnl=" + averagePnl +
                ", winRate=" + winRate +
                ", maxDrawDown=" + maxDrawDown +
                ", profitFactor=" + profitFactor +
                ", cumulativePnl=" + cumulativePnl +
                '}';
    }
}
