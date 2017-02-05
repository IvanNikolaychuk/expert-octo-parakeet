package com.tasks.analyzer.drafts.helpers;

import com.core.db.entity.Candle;
import com.tasks.analyzer.drafts.algorithms.*;

import java.util.List;

public class CandleSequence {
    private int currentIndex;
    private List<Candle> candles;

    public CandleSequence(List<Candle> candles) {
        this.candles = candles;
        this.currentIndex = 0;
    }

    public boolean hasNext() {
        return currentIndex < candles.size();
    }


    public Candle next() {
        return candles.get(currentIndex++);
    }

    public boolean isStrongBullCandle(Candle candle) {
        if (!candles.get(currentIndex - 1).equals(candle)) {
            throw new IllegalStateException("It was expected that passed candle is a current one");
        }
        return StrongBullCandleAlgorithm.isStrongBullCandle(candle);

    }

}
