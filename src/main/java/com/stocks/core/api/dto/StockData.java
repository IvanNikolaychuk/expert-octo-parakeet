package com.stocks.core.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockData {
    @JsonProperty("Symbol")
    public String symbol;

    @JsonProperty("Date")
    public Date date;

    @JsonProperty("Open")
    public BigDecimal open;

    @JsonProperty("High")
    public BigDecimal high;

    @JsonProperty("Low")
    public BigDecimal low;

    @JsonProperty("Close")
    public BigDecimal close;

    @JsonProperty("Volume")
    public BigDecimal volume;
}
