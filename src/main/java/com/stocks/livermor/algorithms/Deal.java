package com.stocks.livermor.algorithms;

import static com.stocks.livermor.algorithms.Deal.Type.SHORT;

public class Deal {
    private final double open;
    private double close;
    private final Type type;
    private double profit;
    private String ticker;

    public Deal(double open, Type type, String ticker) {
        this.open = open;
        this.type = type;
        this.ticker = ticker;
    }

    public Deal(Deal other) {
        this.open = other.open;
        this.type = other.type;
        this.close = other.close;
        this.profit = other.profit;
        this.ticker = other.ticker;

    }

    public enum Type {
        SHORT, LONG
    }

    public void close(double close) {
        this.close = close;
        this.profit = computeProfit(close);
    }

    public double computeProfit(double close) {
        if (type == SHORT) return 100 - close * 100 / open;
        return 100 - open * 100 / close;
    }

    public double computeProfit() {
        return profit;
    }

    public Type getType() {
        return type;
    }
}
