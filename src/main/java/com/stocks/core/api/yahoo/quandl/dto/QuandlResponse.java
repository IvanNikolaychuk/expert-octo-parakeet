package com.stocks.core.api.yahoo.quandl.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.quandl.api.java.QDataset;
import com.quandl.api.java.QuandlConnection;
import lombok.Data;

import java.util.List;

@Data
public class QuandlResponse {

    private Dataset dataset;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonRootName("dataset")
    public static class Dataset {
        private StockDataSet stockDataSet;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown =  true)
    @JsonRootName("data")
    public static class StockDataSet {
        List<StockData> stockDataList;
    }

    public static class StockData {
//        private Strub
    }

    public static void main(String[] args) {
        QuandlConnection q = QuandlConnection.getLimitedConnection();
        QDataset data1 = q.getDatasetBetweenDates("WIKI/AAPL", "2017-02-23", "2017-02-25");
        System.out.println("1");
    }

}
