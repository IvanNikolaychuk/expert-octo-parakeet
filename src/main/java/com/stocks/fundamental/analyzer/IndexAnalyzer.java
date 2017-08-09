package com.stocks.fundamental.analyzer;

import com.stocks.fundamental.dao.IndexChangeAnalyzeDao;
import com.stocks.fundamental.dao.IndexDao;
import com.stocks.fundamental.entity.Index;
import com.stocks.fundamental.entity.Index.Type;
import com.stocks.fundamental.entity.IndexChangeAnalyzeRecord;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

/**
 * @author Ivan Nikolaichuk
 */
public class IndexAnalyzer {
    private static final IndexDao indexDao = new IndexDao();

    public static void main(String[] args) {
        List<IndexChangeAnalyzeRecord> indexChangeAnalyzeRecords = new ArrayList<>();

        Map<Date, Map<Type, Double>> dateToIndexChangeMap = sortByDate(computeIndexChanges(obtainDateToIndexValueMap()));
        for (Map.Entry<Date, Map<Type, Double>> dateToIndexChange : dateToIndexChangeMap.entrySet()) {
            IndexChangeAnalyzeRecord indexChangeAnalyzeRecord = new IndexChangeAnalyzeRecord(dateToIndexChange.getKey());
            indexChangeAnalyzeRecords.add(indexChangeAnalyzeRecord);
            for (Map.Entry<Type, Double> indexTypeToPercChange : dateToIndexChange.getValue().entrySet()) {
                indexChangeAnalyzeRecord.setPercChange(indexTypeToPercChange.getKey(), indexTypeToPercChange.getValue());
            }
        }

        new IndexChangeAnalyzeDao().save(indexChangeAnalyzeRecords);
    }

    private static Map<Date, Map<Type, Double>> computeIndexChanges(Map<Date, Map<Type, Double>> dateToIndexValueMap) {
        Map<Date, Map<Type, Double>> dateToIndexChangeMap = new HashMap<>();

        Map<Type, Double> previousIndexValueMap = null;
        for (Map.Entry<Date, Map<Type, Double>> dateToIndexValue : dateToIndexValueMap.entrySet()) {
            if (previousIndexValueMap != null) {
                dateToIndexChangeMap.put(dateToIndexValue.getKey(),
                        computePercentageMap(previousIndexValueMap, dateToIndexValue.getValue()));
            }
            previousIndexValueMap = dateToIndexValue.getValue();
        }

        return dateToIndexChangeMap;
    }

    private static Map<Type, Double> computePercentageMap(Map<Type, Double> previous, Map<Type, Double> current) {
        Map<Type, Double> indexToPercChangeMap = new HashMap<>();

        for (Map.Entry<Type, Double> indexToValue : current.entrySet()) {
            double currentValue = indexToValue.getValue();
            double prevValue = previous.get(indexToValue.getKey());
            final double percChange =  (100 * currentValue / prevValue) - 100;

            indexToPercChangeMap.put(indexToValue.getKey(), percChange);
        }

        return indexToPercChangeMap;
    }

    private static Map<Date, Map<Type, Double>> obtainDateToIndexValueMap() {
        Map<Date, Map<Type, Double>> dateToIndexValueMap = new HashMap<>();

        List<Index> indexes = indexDao.getAll();

        for (Index index : indexes) {
            dateToIndexValueMap.putIfAbsent(index.getDate(), new HashMap<>());
            dateToIndexValueMap.get(index.getDate()).put(index.getType(), index.getValue());
        }

        return sortByDate(dateToIndexValueMap);
    }

    private static Map<Date, Map<Type, Double>> sortByDate(Map<Date, Map<Type, Double>> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }


}
