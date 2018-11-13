package com.group3.ca3.rest.http;

public class HttpResponse
{

    public final HttpRequest request;
    public final int         responseCode;
    public final String      contents;

    public HttpResponse(HttpRequest request, int responseCode, String contents)
    {
        this.request = request;
        this.responseCode = responseCode;
        this.contents = contents;
    }
}
