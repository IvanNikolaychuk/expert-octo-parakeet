package com.stocks.livermor.constants;

public class Constants {
    public static final int BASIC_CHANGE_PERCENTAGE = 1;
    public static final int BASIC_CHANGE_POINT = 3;

    public enum Rule {
        _6aa,
        _6a,

        _6cc,
        _6c("Во время нисходящего тренда просиходит ралли в 6+ пунктов"),

        _5b,
        _6h,

        _5a,
        _6g,

        _6d,
        _11b,
        _6b,
        _11a,

        _12("Продолжение нисходящего тренда. Новая цена ниже предыдущей"),
        _13("Продолжение естественного ралли. Новая цена выше предыдущей (но нет ни одной пробитой точки)");

        Rule(String explanation) {

        }

        Rule() {
        }


    }
}
