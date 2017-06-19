package com.stocks.technical.tasks.utils.converter;

import com.stocks.technical.core.api.dto.StockData;
import com.stocks.technical.core.db.entity.Candle;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static java.math.BigDecimal.*;

public class StockDataCandleConverter {

    @Test
    public void stockDataWithPositiveTrendHasPositiveBodyLengthAndTrendUp() {
        StockData stockData = createTodaysStock();
        stockData.setOpen(ONE);
        stockData.setClose(valueOf(2));
        stockData.setLow(ONE);
        stockData.setHigh(valueOf(2));

        Candle candle = StockDataToCandleConverter.convert(stockData);
        Assert.assertEquals(candle.getBody(), ONE);
        Assert.assertEquals(candle.getTrend(), Candle.Trend.UP);
    }

    @Test
    public void stockDataWithNegativeTrendHasPositiveBodyLengthAndTrendDown() {
        StockData stockData = createTodaysStock();
        stockData.setOpen(ONE);
        stockData.setClose(ZERO);
        stockData.setLow(ZERO);
        stockData.setHigh(ONE);

        Candle candle = StockDataToCandleConverter.convert(stockData);
        Assert.assertEquals(candle.getBody(), ONE);
        Assert.assertEquals(candle.getTrend(), Candle.Trend.DOWN);
    }

   @Test
    public void percentageProfitNegativeTrend() {
        StockData stockData = createTodaysStock();
        stockData.setOpen(ONE);
        stockData.setClose(ZERO);
        stockData.setLow(ZERO);
        stockData.setHigh(ONE);

        Candle candle = StockDataToCandleConverter.convert(stockData);
        Assert.assertEquals(candle.getPercentageProfit(), valueOf(100).negate());
    }

    @Test
    public void percentageProfitPositiveTrend() {
        StockData stockData = createTodaysStock();
        stockData.setOpen(ONE);
        stockData.setClose(valueOf(2));
        stockData.setLow(ZERO);
        stockData.setHigh(valueOf(2));

        Candle candle = StockDataToCandleConverter.convert(stockData);
        Assert.assertEquals(candle.getPercentageProfit(), valueOf(100));
    }

    @Test
    public void lowShadowIsDefinedCorrectly() {
        StockData stockData = createTodaysStock();
        stockData.setHigh(valueOf(11));
        stockData.setClose(TEN);

        stockData.setLow(valueOf(4));
        stockData.setOpen(valueOf(5));

        Candle candle = StockDataToCandleConverter.convert(stockData);
        Assert.assertEquals(candle.getLowerShadow(), ONE);
    }


    @Test
    public void upShadowIsDefinedCorrectly() {
        StockData stockData = createTodaysStock();
        stockData.setHigh(TEN);
        stockData.setClose(TEN);

        stockData.setLow(valueOf(4));
        stockData.setOpen(valueOf(5));

        Candle candle = StockDataToCandleConverter.convert(stockData);
        Assert.assertEquals(candle.getUpperShadow(), ZERO);
    }


    private StockData createTodaysStock() {
        StockData stockData = new StockData();
        stockData.setDate(new Date());
        return stockData;
    }
}
