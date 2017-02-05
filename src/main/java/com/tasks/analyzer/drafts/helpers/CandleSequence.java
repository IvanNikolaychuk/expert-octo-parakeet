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

    public boolean isStrongBullCandle(Candle target) {
        if (!candles.get(currentIndex - 1).equals(target)) {
            throw new IllegalStateException("It was expected that passed candle is a current one");
        }

        final int indexOfCandleBeforeTargetCandle = currentIndex - 2;
        return indexOfCandleBeforeTargetCandle >= 0 &&
                StrongBullCandleAlgorithm.isStrongBullCandle(candles.get(indexOfCandleBeforeTargetCandle), target);

    }

}
