package com.sibilantsolutions.iplayers.layer.app.http.domain;

import java.util.Map;
import java.util.TreeMap;

public class HttpHeaders
{

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
