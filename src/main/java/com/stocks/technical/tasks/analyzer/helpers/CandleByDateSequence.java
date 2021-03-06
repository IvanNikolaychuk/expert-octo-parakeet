package com.stocks.technical.tasks.analyzer.helpers;

import com.stocks.technical.core.db.entity.Candle;
import com.stocks.technical.tasks.analyzer.patterns.PatternFactory;
import com.stocks.technical.tasks.utils.filters.CandlesFilter;

import java.util.List;

import static com.stocks.technical.tasks.utils.CandleUtils.sort;
import static java.util.Arrays.asList;

public class CandleByDateSequence {
    private int currentIndex;
    private List<Candle> candles;

    public CandleByDateSequence(List<Candle> candles) {
        this.candles = sort(candles, new CandlesFilter.OldDateFirstComparator());
        for (int i = 0; i < candles.size() - 1; i++) {
            if (candles.get(i).getDate().compareTo(candles.get(i + 1).getDate()) > 0) {
                throw new IllegalStateException();
            }
        }
        this.currentIndex = 0;
    }

    public CandleByDateSequence(Candle... candles) {
        this(asList(candles));
    }

    public Candle getCurrent() {
        return candles.get(currentIndex);
    }

    public void setCurrent(Candle newCurrent) {
        for (int index = 0; index < candles.size(); index++) {
            if (candles.get(index).equals(newCurrent)) {
                currentIndex = index;
                return;
            }
        }

        throw new IllegalStateException(newCurrent + " is not found in candle list. Size of candle list: " + candles.size());
    }

    public boolean hasNext() {
        return (currentIndex + 1) < candles.size();
    }

    public Candle next() {
        return candles.get(++currentIndex);
    }

    public boolean hasPrev() {
        return (currentIndex - 1) < candles.size();
    }

    public Candle prev() {
        return candles.get(--currentIndex);
    }

    public Candle.Pattern findPattern() {
        boolean hasPrev = currentIndex - 1 > 0;
        if (!hasPrev) return Candle.Pattern.NONE;

        Candle current = candles.get(currentIndex);
        Candle prev = candles.get(currentIndex - 1);

        return PatternFactory.findPattern(prev, current, candles);
    }

    public Candle back(int value) {
        if (currentIndex - value < 0) return candles.get(0);
        return candles.get(currentIndex - value);
    }
}
