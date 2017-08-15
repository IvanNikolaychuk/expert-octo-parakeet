package com.stocks.livermor.constants;

public class Constants {
    public static final int BASIC_CHANGE_PERCENTAGE = 1;
    public static final int BASIC_CHANGE_POINT = 3;

    public enum Rule {
        _6aa("Во время восходящего тренда происходит реакция в 6+ пунктов, но последняя цена - 3п. " +
                "меньше последней пивтоной точки и между ними не было нисходящего тренда"),
        _6a("Во время восходящего тренда происходит реакция в 6+ пунктов"),

        _6cc,
        _6c("Во время нисходящего тренда просиходит ралли в 6+ пунктов"),

        _5b,
        _6h,

        _5a("Во время естественного ралли происходит ралли 3+ пункта от последней пивотной точки"),
        _6g("Во время естественной реакции происходит ралли 6+ пунктов, но цена ниже последней в естественном ралли"),
        _6g3("Во время вторичного ралли цена превысила последнюю в естественном ралли"),

        _6d("Во время ествественной реакции происходит ралли 6+ пунктов"),
        _6d3("Во время ествественной реакции цена превышает последнюю в восходящем тренде"),
        _11b,
        _6b("Во время ествественного ралли происходит реакция 6+ пунктов"),
        _11a,

        _12("Продолжение нисходящего тренда. Новая цена ниже предыдущей"),
        _12_rally("Продолжение естественного ралли. Новая цена выше предыдущей (но нет ни одной пробитой точки)"),
        _12_reaction("Продолжение естественного реакции. Новая цена ниже предыдущей (но нет ни одной пробитой точки)");

        Rule(String explanation) {

        }

        Rule() {
        }


    }
}
