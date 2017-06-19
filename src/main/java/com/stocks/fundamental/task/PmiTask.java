package com.stocks.fundamental.task;

import com.stocks.fundamental.api.QuandlApi;
import com.stocks.fundamental.dao.IndicatorDao;
import com.stocks.fundamental.entity.Indicator;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Calendar.YEAR;

/**
 * @author Ivan Nikolaichuk
 */
public class PmiTask {
    private final IndicatorDao indicatorDao = new IndicatorDao();

    public static void main(String[] args) {
        new PmiTask().execute();
    }

    private void execute() {
        List<Indicator> filtered = filterByYear(QuandlApi.queryPmi(), 1998);

        Indicator recentIndicator = findRecentIndicator();
        if (recentIndicator == null) {
            indicatorDao.save(filtered);
        } else {
            indicatorDao.save(findNew(filtered, recentIndicator));
        }
    }

    private List<Indicator> findNew(List<Indicator> filtered, Indicator recentIndicator) {
        List<Indicator> newIndicators = new ArrayList<>();
        Date recentDate = recentIndicator.getDate();

        for (Indicator indicator : filtered) {
            if (indicator.getDate().compareTo(recentDate) > 0) {
                newIndicators.add(indicator);
            }
        }

        return newIndicators;
    }

    private Indicator findRecentIndicator() {
        List<Indicator> indicators = indicatorDao.getAll();
        if (indicators == null || indicators.isEmpty()) {
            return null;
        }

        indicators.sort((first, second) -> {
            Date firstDate = first.getDate();
            Date secondDate = second.getDate();

            return secondDate.compareTo(firstDate);
        });

        return indicators.get(0);
    }

    private List<Indicator> filterByYear(List<Indicator> indicators, int yearFrom) {
        return indicators
                .stream()
                .filter(indicator -> {
                    Calendar indicatorDate = Calendar.getInstance();
                    indicatorDate.setTime(indicator.getDate());

                    return indicatorDate.get(YEAR) > yearFrom;
                })
                .collect(Collectors.toList());
    }
}
