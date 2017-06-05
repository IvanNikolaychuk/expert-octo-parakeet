-- у yahoo нет данных
SELECT *
FROM candle
  JOIN company_candle ON candle.id = company_candle.candles_id
WHERE open = -1000 OR close = -1000 OR high = -1000 OR low = -1000 OR volume = -1000;

-- левые данные
SELECT *
FROM candle
WHERE upper_shadow < 0 OR lower_shadow < 0;

SELECT open, close, volume, company_name
FROM candle JOIN company_candle ON candle.id = company_candle.candles_id
WHERE date like '%2016-06-28%' and company_candle.company_name='R';



UPDATE candle
SET
  open              = 60.01,
  close             = 60.44,
  low               = 59.36,
  high              = 60.93,
  volume            = 976000,
  body              = 0.43,
  lower_shadow      = 0.65,
  upper_shadow      = 0.49,
  percentage_profit =  0.71
WHERE id = 126684;