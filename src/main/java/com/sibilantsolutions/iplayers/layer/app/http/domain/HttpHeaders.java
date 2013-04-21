package com.sibilantsolutions.iplayers.layer.app.http.domain;

import java.util.Map;
import java.util.TreeMap;

public class HttpHeaders
{

    final static public String HOST = "Host";
    final static public String USER_AGENT = "User-Agent";
    final static public String ACCEPT = "Accept";
    final static public String ACCEPT_LANGUAGE = "Accept-Language";
    final static public String ACCEPT_ENCODING = "Accept-Encoding";
    final static public String DNT = "DNT";
    final static public String CONNECTION = "Connection";
    final static public String CONTENT_LENGTH = "Content-Length";
    final static public String DATE = "Date";

    //private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> headers = new TreeMap<String, String>( String.CASE_INSENSITIVE_ORDER );

    public String getHeader( String key )
    {
        return headers.get( key );
    }

    public void setHeader( String key, String value )
    {
        headers.put( key, value );
    }

}
