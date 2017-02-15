package com.tasks.analyzer;

import com.core.db.entity.Candle;
import com.tasks.analyzer.helpers.CandleByDateSequence;
import com.tasks.analyzer.trend.Movement;
import com.tasks.analyzer.trend.TrendAnalyser;
import com.tasks.analyzer.trend.TrendData;
import com.tasks.helpers.CandleHelper;
import com.tasks.utils.CandleUtils;
import com.tasks.utils.TimeUtils;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class GapsAnalyserTest {

    @Test
    public void analyseBeginsWithNeighborCandles() {
        Candle todays = CandleHelper.createTodaysCandle();
        todays.setOpen(BigDecimal.ONE);
        todays.setClose(BigDecimal.ONE);
        Candle yesterdays = CandleHelper.createYesterdaysCandle();
        yesterdays.setOpen(BigDecimal.ONE);
        yesterdays.setClose(BigDecimal.ONE);
        Candle twoDaysAgo = new Candle();
        twoDaysAgo.setDate(TimeUtils.subtractDaysFromToday(2));
        twoDaysAgo.setOpen(BigDecimal.ONE);
        twoDaysAgo.setClose(BigDecimal.ONE);

        CandleByDateSequence candleByDateSequence = new CandleByDateSequence(Arrays.asList(todays, yesterdays, twoDaysAgo));

        TrendAnalyser trendAnalyser = mock(TrendAnalyser.class);
        GapsAnalyser gapsAnalyser = new GapsAnalyser(trendAnalyser);
        gapsAnalyser = spy(gapsAnalyser);
        doCallRealMethod().when(trendAnalyser).analyseTrend(any(), any(), any());
        doNothing().when(gapsAnalyser).save(anyList());

        gapsAnalyser.analyse(Arrays.asList(yesterdays), candleByDateSequence);

        verify(trendAnalyser).analyseTrend(todays, candleByDateSequence, Movement.FORWARD);
        verify(trendAnalyser).analyseTrend(twoDaysAgo, candleByDateSequence, Movement.BACK);
    }
}
