package com.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockData {
    @JsonProperty("Symbol")
    private String symbol;

    @JsonProperty("Date")
    private Date date;

    @JsonProperty("Open")
    private BigDecimal open;

    @JsonProperty("High")
    private BigDecimal high;

    @JsonProperty("Low")
    private BigDecimal low;

    @JsonProperty("Close")
    private BigDecimal close;

    @JsonProperty("Volume")
    private BigDecimal volume;
}
