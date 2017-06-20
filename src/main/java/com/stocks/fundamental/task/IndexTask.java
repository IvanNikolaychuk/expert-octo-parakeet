package com.stocks.fundamental.task;

import com.stocks.fundamental.api.QuandlApi;
import com.stocks.fundamental.dao.IndicatorDao;
import com.stocks.fundamental.entity.Index;

import java.util.*;
import java.util.stream.Collectors;

import static com.stocks.fundamental.entity.Index.Type.PMI;
import static com.stocks.fundamental.entity.Index.Type.S_AND_P;
import static java.util.Calendar.YEAR;

/**
 * @author Ivan Nikolaichuk
 */
public class IndexTask {
    private final IndicatorDao indicatorDao = new IndicatorDao();

    public static void main(String[] args) {
        new IndexTask().execute(PMI, "/ISM/MAN_PMI");
        new IndexTask().execute(S_AND_P, "/MULTPL/SP500_REAL_PRICE_MONTH");
    }

    private void execute(Index.Type indexType, String extraUrl) {
        List<Index> filtered = filterByYear(QuandlApi.query(extraUrl, indexType), 1998);

        Index recentIndex = findRecentIndicator(indexType);
        if (recentIndex == null) {
            indicatorDao.save(filtered);
        } else {
            indicatorDao.save(findNew(filtered, recentIndex));
        }
    }

    private List<Index> findNew(List<Index> filtered, Index recentIndex) {
        List<Index> newIndices = new ArrayList<>();
        Date recentDate = recentIndex.getDate();

        for (Index index : filtered) {
            if (index.getDate().compareTo(recentDate) > 0) {
                newIndices.add(index);
            }
        }

        return newIndices;
    }

    private Index findRecentIndicator(Index.Type indexType) {
        List<Index> indices = indicatorDao.getAll()
                .stream()
                .filter((index) -> index.getType() == indexType)
                .collect(Collectors.toList());

        if (indices == null || indices.isEmpty()) {
            return null;
        }

        indices.sort((first, second) -> {
            Date firstDate = first.getDate();
            Date secondDate = second.getDate();

            return secondDate.compareTo(firstDate);
        });

        return indices.get(0);
    }

    private List<Index> filterByYear(List<Index> indices, int yearFrom) {
        return indices
                .stream()
                .filter(index -> {
                    Calendar indicatorDate = Calendar.getInstance();
                    indicatorDate.setTime(index.getDate());

                    return indicatorDate.get(YEAR) > yearFrom;
                })
                .collect(Collectors.toList());
    }
}
