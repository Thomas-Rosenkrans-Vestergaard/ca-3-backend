package com.group3.ca3.rest.http;

import java.util.Map;

public class HttpRequest
{

    public final String              url;
    public final String              method;
    public final Map<String, String> headers;
    public final String              body;

    public HttpRequest(String url, String method, Map<String, String> headers, String body)
    {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.body = body;
    }

    public static HttpRequestBuilder builder()
    {
        return new HttpRequestBuilder();
    }
}
