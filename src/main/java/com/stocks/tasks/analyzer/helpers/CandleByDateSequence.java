package com.stocks.tasks.analyzer.helpers;

import com.stocks.core.db.entity.Candle;
import com.stocks.tasks.analyzer.algorithms.*;
import com.stocks.tasks.analyzer.patterns.PatternFactory;
import com.stocks.tasks.utils.CandleUtils;
import com.stocks.tasks.utils.filters.CandlesFilter;

import java.util.List;

import static java.util.Arrays.asList;

public class CandleByDateSequence {
    private int currentIndex;
    private List<Candle> candles;

    public CandleByDateSequence(List<Candle> candles) {
        this.candles = CandleUtils.sort(candles, new CandlesFilter.OldDateFirstComparator());
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

    public Candle.Pattern findPattern() {
        boolean hasPrev = currentIndex - 1 > 0;
        if (!hasPrev) return Candle.Pattern.NONE;

        Candle current = candles.get(currentIndex);
        Candle prev = candles.get(currentIndex - 1);

        return PatternFactory.findPattern(prev, current);
    }
}
