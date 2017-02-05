package com.tasks.analyzer.drafts.algorithms;

import com.core.db.entity.Candle;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static com.core.api.helpers.Constants.MIN_ACCEPTED_GROW_PERCENTAGE_FOR_STRONG_BULL_CANDLE;
import static com.core.db.entity.Candle.Trend.DOWN;
import static com.core.db.entity.Candle.Trend.UP;
import static com.tasks.helpers.CandleHelper.createTodaysCandle;
import static com.tasks.helpers.CandleHelper.createYesterdaysCandle;

public class StrongBullCandleTest {

    @Test
    public void negativeTrendIsNotConsideredAsStrongBull() {
        Candle target = new Candle();
        target.setTrend(DOWN);

        Assert.assertFalse(StrongBullCandleAlgorithm.isStrongBullCandle(target));
    }

    @Test
    public void positiveTrendWithBigPercentageIsConsideredAsStrongBull() {
        Candle target = createTodaysCandle();
        target.setTrend(UP);

        target.setOpen(BigDecimal.ONE);
        target.setClose(target.getOpen()
                .multiply(MIN_ACCEPTED_GROW_PERCENTAGE_FOR_STRONG_BULL_CANDLE)
                .multiply(BigDecimal.valueOf(100))
        );

        Assert.assertTrue(StrongBullCandleAlgorithm.isStrongBullCandle(target));
    }

    @Test
    public void positiveTrendWithSmallPercentageIsNotConsideredAsStrongBull() {
        Candle target = createTodaysCandle();
        target.setOpen(BigDecimal.valueOf(100));
        target.setTrend(UP);
        target.setClose(target.getOpen().add(BigDecimal.ONE));

        Assert.assertFalse(StrongBullCandleAlgorithm.isStrongBullCandle(target));
    }
}
