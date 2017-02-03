package com.core.api;

import com.core.api.dto.StockData;
import com.core.api.dto.YahooResponse;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class YahooApi {
    public static List<StockData> query(String url) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Queering url: " + url);
        YahooResponse yahooResponse = restTemplate.getForEntity(url, YahooResponse.class).getBody();

        return yahooResponse.getStockData();
    }
}
