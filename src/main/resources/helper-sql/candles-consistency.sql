-- number of stocks

select count(id), company_name from candle
join company_candle on candle.id = company_candle.candles_id
group by company_name
order by count(id)

-- compare avg and max open
select company_name, avg(open), max(open) from candle
join company_candle on candle.id = company_candle.candles_id
group by company_name


select count(id) from candle;
select count(*) from company_candle;