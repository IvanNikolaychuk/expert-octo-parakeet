-- candle analyser
SELECT *
FROM candle
  JOIN company_candle ON candle.id = company_candle.candles_id
WHERE pattern != 'NONE'
ORDER BY date DESC