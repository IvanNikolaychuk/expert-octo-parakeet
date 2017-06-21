package com.stocks.fundamental.update;

import com.stocks.fundamental.api.QuandlApi;
import com.stocks.fundamental.dao.IndexDao;
import com.stocks.fundamental.entity.Index;

import java.util.*;
import java.util.stream.Collectors;

import static com.stocks.fundamental.entity.Index.*;
import static com.stocks.fundamental.entity.Index.Type.PMI;
import static java.util.Calendar.YEAR;

/**
 * @author Ivan Nikolaichuk
 */
public class IndexUpdate {
    private final IndexDao indicatorDao = new IndexDao();

    public static void main(String[] args) {
        new IndexUpdate().execute(PMI, "/ISM/MAN_PMI");
    }

    private void execute(Type indexType, String extraUrl) {
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

    private Index findRecentIndicator(Type indexType) {
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
