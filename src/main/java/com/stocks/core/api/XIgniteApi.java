package com.stocks.core.api;

import com.stocks.core.api.xignite.converter.XigniteJsonConverter;
import com.stocks.core.api.xignite.generator.UrlGenerator;
import com.stocks.core.api.dto.StockData;
import com.stocks.core.api.helpers.Period;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class XIgniteApi {
    public static List<StockData> query(String companyName, Period period) {
        String url = UrlGenerator.generate(companyName, period);
        System.out.println("Queering url " + url);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> jsonResponse = restTemplate.getForEntity(url, String.class);

        return XigniteJsonConverter.convert(jsonResponse.getBody());
    }
}
