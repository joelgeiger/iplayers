package com.sibilantsolutions.iplayers.layer.app.http.domain;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders
{

    private Map<String, String> headers = new HashMap<String, String>();

    public String getHeader( String key )
    {
        return headers.get( key );
    }

    public void setHeader( String key, String value )
    {
        headers.put( key, value );
    }

}
