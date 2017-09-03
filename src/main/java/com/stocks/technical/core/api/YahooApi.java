package com.stocks.technical.core.api;

import com.stocks.fundamental.dao.IndexDao;
import com.stocks.fundamental.entity.Index;
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

import static com.stocks.fundamental.entity.Index.Type.S_AND_P;
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


    public static void main(String[] args) {
        Calendar from = Calendar.getInstance();
        from.set(Calendar.YEAR, 2017);
        from.set(Calendar.MONTH, Calendar.JULY);
        from.set(Calendar.DAY_OF_MONTH, 1);

        Calendar to = Calendar.getInstance();
        query("GAZP.ME", new Pair<>(from.getTime(), to.getTime()));
        List<Index> indexes = new ArrayList<>();
//        for (StockData stockData : query("^GSPC", new Pair<>(from.getTime(), new Date()))) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(stockData.date);
//            calendar.set(Calendar.DAY_OF_MONTH, 25);
//            calendar.add(Calendar.MONTH, -1);
//            calendar.set(Calendar.HOUR_OF_DAY, 0);
//            calendar.set(Calendar.MINUTE, 0);
//            calendar.set(Calendar.SECOND, 0);
//
//            Index index = new Index(S_AND_P, calendar.getTime(), stockData.open.doubleValue());
//            indexes.add(index);
//        }
//
//        new IndexDao().save(indexes);


        IndexDao indexDao = new IndexDao();
        List<Index> toBeSaved = new ArrayList<>();

        for (Index index : indexDao.getAll()) {
            if (index.getType() == S_AND_P) {
                double prev = BigDecimal.valueOf(index.getValue())
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();

                index.setValue(prev);
                toBeSaved.add(index);
            }
        }

        indexDao.update(toBeSaved);
    }


}
