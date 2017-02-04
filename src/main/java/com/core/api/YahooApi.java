package com.core.api;

import com.core.api.dto.StockData;
import com.core.api.dto.YahooResponse;
import com.core.api.dto.YahooSingleStockResponse;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class YahooApi {
    public static List<StockData> query(String url) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Queering url: " + url);

        return restTemplate
                .getForEntity(url, YahooResponse.class)
                .getBody()
                .getStockData();
    }

    public static StockData querySingle(String url) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Queering url: " + url);

        return restTemplate
                .getForEntity(url, YahooSingleStockResponse.class)
                .getBody()
                .getStockData();
    }
}
