package com.gererator;

import com.utils.Period;

import java.text.MessageFormat;

public class RequestGenerator {
    private static final String BASE_URL = "http://query.yahooapis.com/v1/public/yql?format=json&env=store://datatables.org/alltableswithkeys";

    public static String generateFor(String companyName, Period period) {
        return appendNextParameter(BASE_URL, "q", buildQuery(companyName, period));
    }

    private static String appendNextParameter(String baseUrl, String paramName, String paramContent) {
        return baseUrl + "&" + paramName + "=" + paramContent;
    }

    private static String buildQuery(String company, Period period) {
        final String queryTemplate = "select * from yahoo.finance.historicaldata where symbol=\"{0}\" and startDate=\"{1}\" and endDate=\"{2}\"";

        return MessageFormat.format(queryTemplate, company, period.getStartDate().toString(), period.getEndDate().toString());
    }
}
