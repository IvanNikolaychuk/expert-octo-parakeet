package com.tasks.utils.converter;

import com.core.api.dto.StockData;
import com.core.db.entity.Candle;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;

public class StockDataCandleConverter {

    @Test
    public void stockDataWithPositiveTrendHasPositiveBodyLengthAndTrendUp() {
        StockData stockData = createTodaysStock();
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
        StockData stockData = createTodaysStock();
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
        StockData stockData = createTodaysStock();
        stockData.setHigh(BigDecimal.valueOf(11));
        stockData.setClose(BigDecimal.TEN);

        stockData.setLow(BigDecimal.valueOf(4));
        stockData.setOpen(BigDecimal.valueOf(5));

        Candle candle = StockDataToCandleConverter.convert(stockData);
        Assert.assertEquals(candle.getLowerShadow(), BigDecimal.ONE);
    }


    @Test
    public void upShadowIsDefinedCorrectly() {
        StockData stockData = createTodaysStock();
        stockData.setHigh(BigDecimal.TEN);
        stockData.setClose(BigDecimal.TEN);

        stockData.setLow(BigDecimal.valueOf(4));
        stockData.setOpen(BigDecimal.valueOf(5));

        Candle candle = StockDataToCandleConverter.convert(stockData);
        Assert.assertEquals(candle.getUpperShadow(), BigDecimal.ZERO);
    }


    private StockData createTodaysStock() {
        StockData stockData = new StockData();
        stockData.setDate(new Date());
        return stockData;
    }
}
