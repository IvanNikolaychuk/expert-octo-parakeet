package com.stocks.fundamental;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * https://finviz.com/screener.ashx
 *
 *
 var arr = [];
 for(var i = 1; i < 21; i++) {
 var ticker = document.getElementsByTagName("table")[14].getElementsByTagName("tr")[i].getElementsByTagName("td")[1].getElementsByTagName("a")[0].innerHTML;
 arr[i - 1] = ticker;
 }
 arr;

 */
public class Screener {
    private static final String BASE_URI = "http://financials.morningstar.com/ratios/r.html?t=";

    public static void main(String[] args) throws URISyntaxException, IOException {
        final String tickers[] = {
                "SSY", "CSS", "PMT", "SLRC", "EARN", "ALLY", "DO", "SUNS", "IRDM", "FINL", "OSG", "NWLI", "PRU", "MHO", "COF", "FBP", "ZBK", "OFS", "ARCC", "AFSI"
        };

        for(String ticker : tickers) {
            Desktop.getDesktop().browse(new URI(BASE_URI + ticker));
        }
    }
}
