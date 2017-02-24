-- how much performance has business type in this month .
select * from company_growth_statistic_data
join company on company_name = name
where business_type='PHARMACEUTICS' and from_date like '2017-02-01%';

-- what business is most profitable now
select business_type, sum(percentage_profit) / count(id) as profit_per_company
from company_growth_statistic_data
  join company on company_name = name
where from_date like '2017-02-01%'
group by business_type
having count(id) > 3
order by profit_per_company desc;