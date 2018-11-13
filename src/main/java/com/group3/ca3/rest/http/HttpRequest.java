package com.group3.ca3.rest.http;

import java.util.Map;

public class HttpRequest
{

    public final String              url;
    public final String              method;
    public final Map<String, String> headers;

    public HttpRequest(String url, String method, Map<String, String> headers)
    {
        this.url = url;
        this.method = method;
        this.headers = headers;
    }
}
