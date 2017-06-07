package com.stocks.core.db.dao.strongbull;

import com.stocks.core.db.dao.AbstractDao;
import com.stocks.core.db.entity.statistic.stongbull.AfterPatternStatistic;

import java.util.ArrayList;
import java.util.List;

public class AfterPatternStatisticDao extends AbstractDao<AfterPatternStatistic> {
    public void save(List<AfterPatternStatistic> afterPatternStatistics) {
        List<AfterPatternStatistic> filtered = new ArrayList<>();

        for(AfterPatternStatistic afterPatternStatistic : afterPatternStatistics) {
            if (afterPatternStatistic.isValid()) {
                filtered.add(afterPatternStatistic);
            }
        }

        super.save(filtered);
    }
}
