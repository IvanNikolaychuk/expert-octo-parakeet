SELECT
  sum(after_30_day) profit,
  count(*)          periods,
  company_name
FROM after_strong_bull_statistic
GROUP BY company_name
ORDER BY profit;
