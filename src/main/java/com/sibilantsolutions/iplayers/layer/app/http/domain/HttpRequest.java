package com.sibilantsolutions.iplayers.layer.app.http.domain;


public class HttpRequest
{
    private RequestLine requestLine;
    private HttpHeaders httpHeaders;
    private HttpEntity httpEntity;

    public RequestLine getRequestLine()
    {
        return requestLine;
    }
    public void setRequestLine( RequestLine requestLine )
    {
        this.requestLine = requestLine;
    }
    public HttpHeaders getHttpHeaders()
    {
        return httpHeaders;
    }
    public void setHttpHeaders( HttpHeaders httpHeaders )
    {
        this.httpHeaders = httpHeaders;
    }
    public HttpEntity getHttpEntity()
    {
        return httpEntity;
    }
    public void setHttpEntity( HttpEntity httpEntity )
    {
        this.httpEntity = httpEntity;
    }

}
