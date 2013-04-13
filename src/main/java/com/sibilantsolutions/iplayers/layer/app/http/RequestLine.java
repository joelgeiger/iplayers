package com.sibilantsolutions.iplayers.layer.app.http;

public class RequestLine
{
    private HttpMethod method;
    private String requestUri;
    private String httpVersion;

    public String getHttpVersion()
    {
        return httpVersion;
    }

    public HttpMethod getMethod()
    {
        return method;
    }

    public String getRequestUri()
    {
        return requestUri;
    }

    static public RequestLine parse( String str )
    {
        String[] tokens = str.split( "[ \t]+" );
        
            //TODO: Support HTTP 0.9 with no http version token.
        if ( tokens.length != 3 )
            throw new RuntimeException( "Expected 3 tokens" );
        
        RequestLine rl = new RequestLine();
        rl.method = HttpMethod.valueOf( tokens[0] );
        
        return rl;
    }

}
