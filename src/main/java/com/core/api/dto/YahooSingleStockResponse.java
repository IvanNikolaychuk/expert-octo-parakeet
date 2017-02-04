package com.core.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
public class YahooSingleStockResponse {
    private Query query;

    public StockData getStockData() {
        Results results = query.getResults();
        if (results == null) {
            System.out.println("No stocks for requested period, returning empty list.");
            return null;
        }

        return results.getStockData();
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
        private StockData stockData;
    }
}

