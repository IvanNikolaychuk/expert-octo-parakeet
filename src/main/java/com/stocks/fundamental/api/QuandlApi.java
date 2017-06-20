package com.stocks.fundamental.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stocks.fundamental.entity.Index;
import com.stocks.fundamental.entity.Index.Type;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.stocks.technical.core.api.QuandlApi.AUTH_TOKEN;

/**
 * @author Ivan Nikolaichuk
 */
public class QuandlApi {
    private static final String BASE_URL = "https://www.quandl.com/api/v3/datasets";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static List<Index> query(String extraUrl, Type indexType) {
        final String url = BASE_URL + extraUrl + ".json?api_key=" + AUTH_TOKEN;
        final String json = new RestTemplate().getForEntity(url, String.class).getBody();

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonArray mainArray = jsonObject.get("dataset").getAsJsonObject().get("data").getAsJsonArray();

        List<Index> indices = new ArrayList<>();
        for (int i = 0; i < mainArray.size(); i++) {
            JsonArray subArray = mainArray.get(i).getAsJsonArray();
            indices.add(convert(subArray, indexType));
        }

        return indices;
    }

    private static Index convert(JsonArray subArray, Type indexType) {
        String dateStr = subArray.get(0).getAsString();
        try {
            Calendar calendar = Calendar.getInstance();
            Date date = simpleDateFormat.parse(dateStr);
            calendar.setTime(date);
            calendar.set(Calendar.DAY_OF_MONTH, 25);

            if (indexType == Type.S_AND_P) {
                calendar.add(Calendar.MONTH, -1);
            }

            date = calendar.getTime();

            double value = subArray.get(1).getAsDouble();
            return new Index(indexType, date, value);
        } catch (ParseException e) {
            throw new RuntimeException("Cannot parse date: " + dateStr);
        }
    }
}
