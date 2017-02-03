package com;

import com.api.YahooApi;
import com.dto.StockData;
import com.gererator.RequestGenerator;
import com.utils.Period;

import java.util.List;

import static com.utils.Period.Date.february;
import static com.utils.Period.Date.january;

public class Main {

    public static void main(String[] args) {
        String url = RequestGenerator.generateFor("BMY", Period.of(january(1), february(3)));
        List<StockData> stockDataList = YahooApi.query(url);
    }

}
    