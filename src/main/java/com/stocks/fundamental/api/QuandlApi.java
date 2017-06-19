package com.stocks.fundamental.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stocks.fundamental.entity.Indicator;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.stocks.fundamental.entity.Indicator.Type.PMI;
import static com.stocks.technical.core.api.QuandlApi.*;

/**
 * @author Ivan Nikolaichuk
 */
public class QuandlApi {
    private static final String BASE_URL = "https://www.quandl.com/api/v3/datasets";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static List<Indicator> queryPmi() {
        final String url = BASE_URL + "/ISM/MAN_PMI.json?api_key=" + AUTH_TOKEN;
        final String json = new RestTemplate().getForEntity(url, String.class).getBody();

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray mainArray = jsonObject.get("dataset").getAsJsonObject().get("data").getAsJsonArray();

        List<Indicator> indicators = new ArrayList<>();
        for (int i = 0; i < mainArray.size(); i++) {
            JsonArray subArray = mainArray.get(i).getAsJsonArray();
            indicators.add(convert(subArray));
        }

        return indicators;
    }

    private static Indicator convert(JsonArray subArray) {
        String dateStr = subArray.get(0).getAsString();
        try {
            Date date = simpleDateFormat.parse(dateStr);
            double value = subArray.get(1).getAsDouble();
            return new Indicator(PMI, date, value);
        } catch (ParseException e) {
            throw new RuntimeException("Cannot parse date: " + dateStr);
        }
    }
}
