package com.sibilantsolutions.iplayers.layer.app.http;

public class RequestLine
{
    final static public int MAX_METHOD_LEN = 128;       //TODO
    final static public int MAX_REQUEST_URI_LEN = 1024; //TODO
    final static public int MAX_HTTP_VERSION_LEN = 32;  //TODO
    
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
        rl.requestUri = tokens[1];
        rl.httpVersion = tokens[2];
        
        return rl;
    }

}
