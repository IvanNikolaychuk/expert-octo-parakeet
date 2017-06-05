package com.stocks.core.db.dao.strongbull;

import com.stocks.core.db.dao.AbstractDao;
import com.stocks.core.db.entity.statistic.stongbull.AfterStrongBullStatistic;

import java.util.ArrayList;
import java.util.List;

public class AfterStrongBullStatisticDao extends AbstractDao<AfterStrongBullStatistic> {
    public void save(List<AfterStrongBullStatistic> afterStrongBullStatistics) {
        List<AfterStrongBullStatistic> filtered = new ArrayList<>();

        for(AfterStrongBullStatistic afterStrongBullStatistic : afterStrongBullStatistics) {
            if (afterStrongBullStatistic.isValid()) {
                filtered.add(afterStrongBullStatistic);
            }
        }

        super.save(filtered);
    }
}
