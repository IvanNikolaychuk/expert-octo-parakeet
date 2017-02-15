select count(id), company_name from candle
join company_candle on candle.id = company_candle.candles_id
group by company_name
