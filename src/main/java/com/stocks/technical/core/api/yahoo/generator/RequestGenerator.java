package com.stocks.technical.core.api.yahoo.generator;


import javafx.util.Pair;

import java.text.MessageFormat;
import java.util.Date;

public class RequestGenerator {
    private static final String BASE_URL = "https://query2.finance.yahoo.com/v8/finance/chart/{0}?formatted=true" +
            "&crumb=BEbgjvn1CG8&lang=en-US&region=US&period1={1}&period2={2}&interval=1mo&events=div%7Csplit&corsDomain=finance.yahoo.com";

    public static String generateFor(String companyName, Pair<Date, Date> period) {
        String fromTimestamp = ("" + period.getKey().getTime() / 1000).trim();
        String toTimestamp = ("" + period.getValue().getTime() / 1000).trim();
        return MessageFormat.format(BASE_URL, companyName, fromTimestamp, toTimestamp);
    }
}
