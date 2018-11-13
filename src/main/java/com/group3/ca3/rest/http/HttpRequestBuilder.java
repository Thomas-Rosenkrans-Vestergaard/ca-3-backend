package com.group3.ca3.rest.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestBuilder
{

    private String              url;
    private String              method;
    private Map<String, String> headers = new HashMap<>();

    private String              jsonBody;
    private Map<String, String> urlEncodedBody = new HashMap<>();

    private enum ContentType
    {
        JSON,
        WWWURLENC
    }

    private ContentType contentType;

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

    public HttpRequestBuilder post()
    {
        return method("POST");
    }

    public HttpRequestBuilder post(String url)
    {
        url(url);
        return post();
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

    public HttpRequestBuilder contentType(String contentType)
    {
        return header("Content-Type", contentType);
    }

    public HttpRequestBuilder urlEncodedBody(String key, String value)
    {
        urlEncodedBody.put(key, value);
        this.contentType = ContentType.WWWURLENC;

        return contentType("application/x-www-form-urlencoded");
    }

    public HttpRequestBuilder urlEncodedBody(Map<String, String> values)
    {
        urlEncodedBody.putAll(values);
        this.contentType = ContentType.WWWURLENC;

        return contentType("application/x-www-form-urlencoded");
    }

    public HttpRequestBuilder jsonBody(String json)
    {
        contentType("application/json");
        this.jsonBody = json;
        this.contentType = ContentType.JSON;

        return this;
    }

    public HttpRequest build()
    {
        return new HttpRequest(url, method, headers, getBody());
    }

    private String getBody()
    {
        if (contentType == ContentType.WWWURLENC) {
            StringBuilder data = new StringBuilder();
            for (Map.Entry<String, String> entry : urlEncodedBody.entrySet()) {
                data.append(entry.getKey());
                data.append("=");
                data.append(entry.getValue());
                data.append("&");
            }

            return data.toString();
        }

        return jsonBody;
    }
}
