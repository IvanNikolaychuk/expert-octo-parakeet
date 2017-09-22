package com.stocks.technical.core.api;

import com.stocks.technical.core.api.dto.StockData;
import javafx.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
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
                if (exception.getStatusCode() == HttpStatus.BAD_REQUEST) {
                    System.out.println("Bad request.");
                    return new ArrayList<>();
                } else {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException ignored) {
            }
            return query(companyName, period);
        }
    }



}
