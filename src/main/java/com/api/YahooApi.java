package com.api;

import com.dto.StockData;
import com.dto.YahooResponse;
import com.gererator.RequestGenerator;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class YahooApi {
    public static List<StockData> query(String url) {
        RestTemplate restTemplate = new RestTemplate();

        YahooResponse yahooResponse = restTemplate.getForEntity(url, YahooResponse.class).getBody();

        return yahooResponse.getStockData();
    }
}
