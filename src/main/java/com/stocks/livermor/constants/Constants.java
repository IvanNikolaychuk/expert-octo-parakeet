package com.stocks.livermor.constants;

public class Constants {
    public static final int BASIC_CHANGE_PERCENTAGE = 1;
    public static final int BASIC_CHANGE_POINT = 3;

    public enum Rule {
        // Восходящий тренд
        _6aa("Во время восходящего тренда происходит реакция в 6+ пунктов, но последняя цена - 3п. " +
                "меньше последней пивтоной точки и между ними не было нисходящего тренда"),
        _6a("Во время восходящего тренда происходит реакция в 6+ пунктов"),

        // Нисходящий тренд
        _6cc,
        _6c("Во время нисходящего тренда просиходит ралли в 6+ пунктов"),

        // Естественная реакция
        _5b("Во время естественной/вторичной реакции прооисходит реакция 3+ пункта от последней пивотной точки"),
        _6d("Во время ествественной реакции происходит ралли 6+ пунктов"),
        _6d3("Во время ествественной реакции цена превышает последнюю в восходящем тренде"),
        _6g("Во время естественной реакции происходит ралли 6+ пунктов, но цена ниже последней в естественном ралли"),
        _11a("Во время естественной реакции появляется цена выше последей в восходящем тренде"),

        // Естественное ралли
        _5a("Во время естественного ралли происходит ралли 3+ пункта от последней пивотной точки"),
        _6b("Во время ествественного ралли происходит реакция 6+ пунктов"),
        _6h("Во время естественного/вторичного ралли происходит реакция 6+ пунктов, но цена выше последней в естественной реакции"),


        // Вторичное ралли
        _6g3("Во время вторичного ралли цена превысила последнюю в естественном ралли"),

        _11b,


        _12_rally("Продолжение естественного ралли. Новая цена выше предыдущей (но нет ни одной пробитой точки)"),
        _12_reaction("Продолжение естественного реакции. Новая цена ниже предыдущей (но нет ни одной пробитой точки)"),
        _12_secondary_reaction("Продолжение вторичной реакции. Новая цена ниже предыдущей (но не ниже последней в естественной реакции)"),
        _12_secondary_rally("Продолжение вторичного ралли. Новая цена выше предыдущей (но не выше последней в естественном ралли)"),
        _12_upper("Продолжение восходящего тренда. Новая цена выше предыдущей"),
        _12_down("Продолжение нисходящего тренда. Новая цена ниже предыдущей");

        Rule(String explanation) {

        }

        Rule() {
        }


    }
}
