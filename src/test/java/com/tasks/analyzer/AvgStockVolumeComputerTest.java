package com.tasks.analyzer;

import com.core.db.entity.Candle;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AvgStockVolumeComputerTest {
    @Test
    public void avgVolumeCalculatedCorrectly() {
        List<Candle> candles = Arrays.asList(
                createCandle(100d),
                createCandle(200d),
                createCandle(300d)
        );

        assertEquals(new AvgStockVolumeComputer().calculateAvgVolume(candles).doubleValue(), 200, 0.001);
    }

    private Candle createCandle(double volume) {
        Candle candle = new Candle();
        candle.setVolume(BigDecimal.valueOf(volume));

        return candle;
    }

}
