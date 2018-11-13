package com.group3.ca3.rest.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpClient
{

    public HttpResponse perform(HttpRequest httpRequest) throws HttpException
    {
        try {
            URL               newUrl     = new URL(httpRequest.url);
            HttpURLConnection connection = (HttpURLConnection) newUrl.openConnection();
            connection.setRequestMethod(httpRequest.method);
            for (Map.Entry<String, String> entry : httpRequest.headers.entrySet()) {
                connection.setRequestProperty(entry.getKey(), entry.getValue());
            }

            if (httpRequest.body != null) {
                connection.setDoOutput(true);
                try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                    wr.write(httpRequest.body.getBytes("UTF-8"));
                }
            }

            int          statusCode = connection.getResponseCode();
            StringBuffer content    = new StringBuffer();

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
            }

            return new HttpResponse(httpRequest, statusCode, content.toString());
        } catch (IOException e) {
            throw new HttpException(e);
        }
    }
}
