package com.stocks.technical.core.api;

import com.stocks.technical.core.api.dto.StockData;
import javafx.util.Pair;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

import static com.stocks.technical.core.api.yahoo.convertor.YahooJsonConverter.convert;
import static com.stocks.technical.core.api.yahoo.generator.RequestGenerator.generateFor;

public class YahooApi {
    public static List<StockData> query(String companyName, Pair<Date, Date> period) {
        String url = generateFor(companyName, period);
        System.out.println("Queering url: " + url);

        RestTemplate restTemplate = new RestTemplate();
        try {
            String json = restTemplate.getForEntity(url, String.class).getBody();
            return convert(json);
        } catch (HttpClientErrorException exception) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return query(companyName, period);
        }
    }


}
