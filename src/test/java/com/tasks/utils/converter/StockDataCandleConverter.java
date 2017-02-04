package com.tasks.utils.converter;

import com.core.api.dto.StockData;
import com.core.db.entity.Candle;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class StockDataCandleConverter {

    @Test
    public void stockDataWithPositiveTrendHasPositiveBodyLengthAndTrendUp() {
        StockData stockData = new StockData();
        stockData.setOpen(BigDecimal.ZERO);
        stockData.setClose(BigDecimal.ONE);
        stockData.setLow(BigDecimal.ZERO);
        stockData.setHigh(BigDecimal.ONE);

        Candle candle = StockDataToCandleConverter.convert(stockData);
        Assert.assertEquals(candle.getBody(), BigDecimal.ONE);
        Assert.assertEquals(candle.getTrend(), Candle.Trend.UP);
    }

    @Test
    public void stockDataWithNegativeTrendHasPositiveBodyLengthAndTrendDown() {
        StockData stockData = new StockData();
        stockData.setOpen(BigDecimal.ONE);
        stockData.setClose(BigDecimal.ZERO);
        stockData.setLow(BigDecimal.ZERO);
        stockData.setHigh(BigDecimal.ONE);

        Candle candle = StockDataToCandleConverter.convert(stockData);
        Assert.assertEquals(candle.getBody(), BigDecimal.ONE);
        Assert.assertEquals(candle.getTrend(), Candle.Trend.DOWN);
    }

    @Test
    public void lowShadowIsDefinedCorrectly() {
        StockData stockData = new StockData();
        stockData.setHigh(BigDecimal.valueOf(11));
        stockData.setClose(BigDecimal.TEN);

        stockData.setLow(BigDecimal.valueOf(4));
        stockData.setOpen(BigDecimal.valueOf(5));

        Candle candle = StockDataToCandleConverter.convert(stockData);
        Assert.assertEquals(candle.getLowerShadow(), BigDecimal.ONE);
    }


    @Test
    public void upShadowIsDefinedCorrectly() {
        StockData stockData = new StockData();
        stockData.setHigh(BigDecimal.TEN);
        stockData.setClose(BigDecimal.TEN);

        stockData.setLow(BigDecimal.valueOf(4));
        stockData.setOpen(BigDecimal.valueOf(5));

        Candle candle = StockDataToCandleConverter.convert(stockData);
        Assert.assertEquals(candle.getUpperShadow(), BigDecimal.ZERO);
    }
}
