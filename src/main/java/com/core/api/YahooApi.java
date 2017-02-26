package com.core.api;

import com.core.api.yahoo.dto.StockData;
import com.core.api.yahoo.dto.YahooResponse;
import com.core.api.yahoo.dto.YahooSingleStockResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class YahooApi {
    public static List<StockData> query(String url) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Queering url: " + url);

        ResponseEntity<YahooResponse> responseEntity;
        try {
            responseEntity = restTemplate
                    .getForEntity(url, YahooResponse.class);
        } catch (HttpClientErrorException exception) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return query(url);
        }

        return responseEntity
                .getBody()
                .getStockData();
    }


    public static StockData querySingle(String url) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Queering url: " + url);

        ResponseEntity<YahooSingleStockResponse> responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url, YahooSingleStockResponse.class);
        } catch (HttpClientErrorException exception) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            responseEntity = restTemplate.getForEntity(url, YahooSingleStockResponse.class);
        }

        return responseEntity
                .getBody()
                .getStockData();
    }
}
