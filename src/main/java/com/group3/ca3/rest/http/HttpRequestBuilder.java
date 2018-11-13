package com.group3.ca3.rest.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestBuilder
{

    private String              url;
    private String              method;
    private Map<String, String> headers = new HashMap<>();

    public static HttpRequestBuilder getInstance()
    {
        return new HttpRequestBuilder();
    }

    public HttpRequestBuilder url(String url)
    {
        this.url = url;

        return this;
    }

    public HttpRequestBuilder url(String url, Object... params)
    {
        return url(String.format(url, params));
    }

    public HttpRequestBuilder method(String method)
    {
        this.method = method;

        return this;
    }

    public HttpRequestBuilder get()
    {
        return method("GET");
    }

    public HttpRequestBuilder get(String url)
    {
        get();
        return url(url);
    }

    public HttpRequestBuilder header(String key, String value)
    {
        this.headers.put(key, value);

        return this;
    }

    public HttpRequestBuilder acceptJSON()
    {
        return header("Accept", "application/json");
    }

    public HttpRequest build()
    {
        return new HttpRequest(url, method, headers);
    }
}
