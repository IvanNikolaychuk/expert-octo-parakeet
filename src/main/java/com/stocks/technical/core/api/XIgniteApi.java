package com.stocks.technical.core.api;

import com.stocks.technical.core.api.xignite.converter.XigniteJsonConverter;
import com.stocks.technical.core.api.xignite.generator.UrlGenerator;
import com.stocks.technical.core.api.dto.StockData;
import com.stocks.technical.core.api.helpers.Period;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.stocks.technical.core.api.helpers.Period.Date.build;

public class XIgniteApi {
    public static List<StockData> query(String companyName, Period period) {
        String url = UrlGenerator.generate(companyName, period);
        System.out.println("Queering url " + url);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> jsonResponse = restTemplate.getForEntity(url, String.class);

        return XigniteJsonConverter.convert(jsonResponse.getBody());
    }

    public static void main(String[] args) {
        query("GAZP", Period.of(build(2016, 1, 1), build(2016, 1, 2)));
    }
}
