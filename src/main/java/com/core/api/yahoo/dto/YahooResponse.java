package com.core.api.yahoo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class YahooResponse {
    private Query query;

    public List<StockData> getStockData() {
        Results results = query.getResults();
        if (results == null) {
            System.out.println("No stocks for requested period, returning empty list.");
            return new ArrayList<>();
        }

        return results.getStockDataList();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonRootName("query")
    @Data
    public static class Query {
        private Results results;

        @JsonProperty("results")
        public Results getResults() {
            return results;
        }

    }

    @Data
    @JsonRootName("results")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Results {
        @JsonProperty("quote")
        private List<StockData> stockDataList;
    }
}


