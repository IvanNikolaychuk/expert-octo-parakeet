package com.stocks.technical.core.api.quandl.java.query;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a MetadataQuery to get metadata about
 * a Quandl dataset. 
 * 
 * @author Michael Diamond
 * @since  2014-6-6
 */
public class MetadataQuery implements Query {
    private final String qCode;
    
    /*package*/ public MetadataQuery(String qCode) {
        this.qCode = checkNotNull(qCode);
    }
    
    public String getQCode() {
        return qCode;
    }

    @Override
    public Map<String, String> getParameterMap() {
        return ImmutableMap.of();
    }
}
