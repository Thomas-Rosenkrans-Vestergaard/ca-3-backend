package com.group3.ca3.rest;


import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateConnection {

    public String sendGetRequest(String URL, boolean apiCode) throws IOException {
        String url = URL;
        URL obj = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("Accept", "application/json");
        httpConnection.setRequestProperty("Content-Type", "application/json");
        if(apiCode){
            httpConnection.setRequestProperty("X-Mashape-Key", "RW266iQrnBmshH28TA2TN5GQmdHpp1QYvt1jsnQlO3vvFK36pM");
        }
        httpConnection.connect();
        int responseCode = httpConnection.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(httpConnection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        httpConnection.disconnect();
        return response.toString();
    }
}
