package com.stocks.tasks.analyzer.helpers;

import com.stocks.core.db.entity.Candle;
import com.stocks.tasks.analyzer.algorithms.*;
import com.stocks.tasks.utils.CandleUtils;
import com.stocks.tasks.utils.filters.CandlesFilter;

import java.util.List;

public class CandleByDateSequence {
    private int currentIndex;
    private List<Candle> candles;

    public CandleByDateSequence(List<Candle> candles) {
        this.candles = CandleUtils.sort(candles, new CandlesFilter.OldDateFirstComparator());
        for (int i = 0; i < candles.size() - 1; i++) {
            if (candles.get(i).getDate().compareTo(candles.get(i + 1).getDate() ) > 0) {
                throw new IllegalStateException();
            }
        }
        this.currentIndex = 0;
    }

    public boolean hasNext() {
        return currentIndex < candles.size();
    }

    public boolean hasNext(int afterCurrent) {
        return currentIndex + afterCurrent < candles.size();
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

    public boolean isStrongBearCandle(Candle target) {
        if (!candles.get(currentIndex - 1).equals(target)) {
            throw new IllegalStateException("It was expected that passed candle is a current one");
        }

        final int indexOfCandleBeforeTargetCandle = currentIndex - 2;
        return indexOfCandleBeforeTargetCandle >= 0 &&
                StrongBeerCandleAlgorithm.isStrongBearCandle(candles.get(indexOfCandleBeforeTargetCandle), target);
    }

    public boolean isDojiCandle(Candle target) {
        return DojiCandleAlgorithm.isDojiCandle(target);
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
                StrongGapRiseCandleAlgorithm.isStrongGapGrowCandle(candles.get(indexOfCandleBeforeTargetCandle), target);
    }
}
