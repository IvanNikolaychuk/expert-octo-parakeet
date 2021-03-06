package com.stocks.technical.core.api.quandl.java.util;

import com.google.common.base.Supplier;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;

/**
 * Class to centralize HTTP requests, specifically to enable easy mocking of HTTP requests.
 * 
 * Currently this is an implementation detail - i.e. users of the library shouldn't need to
 * see or use this class.  However since it wraps HttpClient callers could have a problem
 * with it making un-managed HTTP requests.  Ideally, QuandlConnection should let users
 * pass in their own HttpController, or perhaps even better, we should mock HttpClient
 * or HttpGet directly, rather than use this wrapper.  As a first pass, this is good enough. 
 * 
 * @author Michael Diamond
 * @since  2014-06-06
 */
public interface HttpController extends AutoCloseable {
    public String getContents(String url) throws HttpResponseException;
    
    @Override
    public void close() throws IOException;
    
    public static class Real implements HttpController {
        public static final Supplier<HttpController> SUPPLIER = new Supplier<HttpController>() {
            @Override
            public HttpController get() {
                return new Real();
            }};
        
        private static final ResponseHandler<String> RESPONSE_HANDLER = new BasicResponseHandler();
        

        /**
         * Execute HTTP requests, raising exceptions on bad response codes.  Returns
         * the page contents as a String.
         */
        // TODO do we need to load pages into memory?  Can we stream through it instead?
        @Override
        public String getContents(String url) throws HttpResponseException {
                HttpGet get = new HttpGet(url);
                // TODO how does this handle encoding?
                return null;
        }

        @Override
        public void close() throws IOException {
//            client.close();
        }
        
    }
}
