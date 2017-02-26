package com.core.api.yahoo.quandl.generator;

import com.core.api.yahoo.helpers.Period;

import java.util.Calendar;

import static com.core.api.yahoo.helpers.Period.Date.forDate;

public class RequestGenerator {
    private static final String BASE_URL = "https://www.quandl.com/api/v3/datasets/WIKI/";
//    private static final String API_KEY = "BxyFSeF3zsXjZbVErHyZ";

    public static String generateFor(String companyName, Period period) {
        String url = BASE_URL + companyName + ".json?";

        url = appendParameter(url, "start_date", period.getStartDate().toString());
        url = appendParameter(url, "end_date", period.getEndDate().toString());

        return url;
    }

    private static String appendParameter(String baseUrl, String paramName, String paramContent) {
        if (baseUrl.contains("?")) {
            return baseUrl + "&" + paramName + "=" + paramContent;
        } else {
            return baseUrl + "?" + paramName + "=" + paramContent;
        }
    }


    public static void main(String[] args) {
        Calendar from = Calendar.getInstance();
        from.set(2017, Calendar.FEBRUARY, 23);

        Calendar to = Calendar.getInstance();
        to.set(2017, Calendar.FEBRUARY, 25);

        System.out.println(RequestGenerator.generateFor("AAPL", Period.of(forDate(from), forDate(to))));
    }
}
