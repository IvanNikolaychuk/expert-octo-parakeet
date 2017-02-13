package com.tasks.analyzer.drafts.helpers;

import com.core.db.entity.Candle;
import com.tasks.analyzer.drafts.algorithms.*;
import com.tasks.utils.CandleUtils;
import com.tasks.utils.filters.CandlesFilter;

import java.util.List;

public class CandleByDateSequence {
    private int currentIndex;
    private List<Candle> candles;

    public CandleByDateSequence(List<Candle> candles) {
        this.candles = CandleUtils.sort(candles, new CandlesFilter.OldDateFirstComparator());
        this.currentIndex = 0;
    }

    public boolean hasNext() {
        return currentIndex < candles.size();
    }

    public boolean hasPrev() {
        return currentIndex - 1 >= 0;
    }

    public Candle next() {
        return candles.get(currentIndex++);
    }

    public Candle prev() {
        return candles.get(--currentIndex);
    }

    public boolean isStrongBullCandle(Candle target) {
        if (!candles.get(currentIndex - 1).equals(target)) {
            throw new IllegalStateException("It was expected that passed candle is a current one");
        }

        final int indexOfCandleBeforeTargetCandle = currentIndex - 2;
        return indexOfCandleBeforeTargetCandle >= 0 &&
                StrongBullCandleAlgorithm.isStrongBullCandle(candles.get(indexOfCandleBeforeTargetCandle), target);
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

    public boolean isStrongGapFallCandle(Candle target) {
        if (!candles.get(currentIndex - 1).equals(target)) {
            throw new IllegalStateException("It was expected that passed candle is a current one");
        }

        final int indexOfCandleBeforeTargetCandle = currentIndex - 2;

        return indexOfCandleBeforeTargetCandle >= 0 &&
                StrongGapFallCandleAlgorithm.isStrongGapFallCandle(candles.get(indexOfCandleBeforeTargetCandle), target);
    }

    public boolean isStrongGapRiseCandle(Candle target) {
        if (!candles.get(currentIndex - 1).equals(target)) {
            throw new IllegalStateException("It was expected that passed candle is a current one");
        }

        final int indexOfCandleBeforeTargetCandle = currentIndex - 2;

        return indexOfCandleBeforeTargetCandle >= 0 &&
                StrongGapRiseCandleAlgorithm.isStrongGapRiseCandle(candles.get(indexOfCandleBeforeTargetCandle), target);
    }
}
