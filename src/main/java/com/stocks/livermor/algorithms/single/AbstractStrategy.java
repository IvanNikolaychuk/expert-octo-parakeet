package com.stocks.livermor.algorithms.single;

import com.stocks.livermor.algorithms.Deal;
import com.stocks.livermor.entity.LivermorSingleCompany;
import com.stocks.livermor.utils.RecordsHolder;
import com.stocks.technical.core.db.dao.LivermorSingleCompanyDao;
import com.stocks.technical.core.db.dao.RecordDao;

import java.util.ArrayList;
import java.util.List;

import static com.stocks.livermor.algorithms.Constants.COMMISSION_PER_DEAL;

public abstract class AbstractStrategy {
    RecordDao recordDao = new RecordDao();
    List<Deal> deals = new ArrayList<>();

    public void computeProfit() {
        double profit = 0;

        for (Deal deal : deals) {
            profit += deal.computeProfit();
            profit -= COMMISSION_PER_DEAL;
        }

        System.out.println("Profit: " + profit);
    }

    public abstract List<Deal> process(RecordsHolder recordsHolder);

    public void run() {
        for (LivermorSingleCompany livermorSingleCompany : new LivermorSingleCompanyDao().getAll()) {
            List<Deal> newDeals = process(new RecordsHolder(recordDao.getAll(livermorSingleCompany.getTicker())));
            deals.addAll(newDeals);
        }

        computeProfit();
    }

    public static void main(String[] args) {
        AbstractStrategy abstractStrategy = new SellOrBuyOnTrendChangeWithSignals();
        abstractStrategy.run();
    }
}
