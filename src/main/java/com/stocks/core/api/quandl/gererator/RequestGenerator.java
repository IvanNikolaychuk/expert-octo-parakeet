package com.stocks.core.api.quandl.gererator;

import com.stocks.core.api.helpers.Period;

public class RequestGenerator {
    private static final String BASE_URL = "https://www.quandl.com/api/v3/datasets/WIKI/";
    private static final String AUTH_TOKEN = "BxyFSeF3zsXjZbVErHyZ";

    public static String generateFor(String companyName, Period period) {
        String url = BASE_URL + companyName + ".json?";

        url = appendParameter(url, "trim_start", period.getStartDate().toString());
        url = appendParameter(url, "trim_end", period.getEndDate().toString());
        url = appendParameter(url, "auth_token", AUTH_TOKEN);

        return url;
    }

    private static String appendParameter(String baseUrl, String paramName, String paramContent) {
        if (baseUrl.contains("?")) {
            return baseUrl + "&" + paramName + "=" + paramContent;
        }

        return baseUrl + "?" + paramName + "=" + paramContent;

    }
    }
