package rest;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateConnection {

    public static void main(String[] args) throws Exception {
        CreateConnection comeon = new CreateConnection();
        comeon.sendGetRequest("https://dog.ceo/api/breeds/image/random");
    }

    public void sendGetRequest(String URL) throws IOException {
        String url = URL;
        URL obj = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) obj.openConnection();
        httpConnection.setRequestMethod("GET");
        httpConnection.setRequestProperty("Accept", "application/json");
        //httpConnection.setRequestProperty("Content-Type", "application/json");
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

        System.out.println(response.toString());
    }
}
