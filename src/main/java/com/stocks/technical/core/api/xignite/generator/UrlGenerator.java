package com.stocks.technical.core.api.xignite.generator;

import com.stocks.technical.core.api.helpers.Period;

import java.text.MessageFormat;

public class UrlGenerator {
    /**
     * http://www.investopedia.com/markets/stocks/bmy/ - здесь смотреть токен
     */
    private static final String BASE_URL_START = "http://www.xignite.com/xGlobalHistorical.json/GetGlobalHistoricalQuotesRange?IdentifierType=Symbol&Identifier={0}" +
            "&StartDate={1}&EndDate={2}&AdjustmentMethod=SplitAndProportionalCashDividend" +
            "&_token=15B2186D55CB1AEFD12E7C2C2DE9CBB9110DFB516C5307188C5E44131653A7F1F21A023038E058A60D7E82D208944A1AAE86E320" +
            "&_token_userid=46384";


    public static String generate(String ticker, Period period) {
        return MessageFormat.format(BASE_URL_START, ticker, period.getStartDate().toString(), period.getEndDate().toString());
    }
}
