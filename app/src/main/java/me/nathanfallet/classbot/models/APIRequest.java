package me.nathanfallet.classbot.models;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import androidx.annotation.Nullable;

public class APIRequest extends AsyncTask<Void, Void, Object> {

    private String method;
    private String host;
    private String path;
    private HashMap<String, Object> queryItems;
    private JSONObject body;
    private CompletionHandler completionHandler;
    private int httpResult;

    public APIRequest(String method, String host, String path, CompletionHandler completionHandler) {
        // Get request parameters
        this.method = method;
        this.host = host;
        this.path = path;
        this.queryItems = new HashMap<>();
        this.completionHandler = completionHandler;
    }

    public APIRequest with(String name, Object value) {
        queryItems.put(name, value);
        return this;
    }

    public APIRequest withBody(JSONObject body) {
        this.body = body;
        return this;
    }

    public URL getURL() {
        try {
            String protocol = "https";

            URI uri = new URI(protocol, null, host, 443, path, getQuery(), null);

            return uri.toURL();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getQuery() {
        StringBuilder sb = new StringBuilder();

        if (!queryItems.isEmpty()) {
            for (String key : queryItems.keySet()) {
                if (!sb.toString().isEmpty()) {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(queryItems.get(key));
            }
        }

        return sb.toString();
    }

    @Override
    protected Object doInBackground(Void... objects) {
        try {
            // Create the request based on give parameters
            HttpsURLConnection con = (HttpsURLConnection) getURL().openConnection();
            con.setRequestMethod(method);

            // Set body
            if (body != null) {
                con.setDoOutput(true);
                con.setRequestProperty("Content-Type", "application/json; utf-8");
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = body.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }

            // Launch the request to server
            con.connect();

            // Get data and response
            StringBuilder sb = new StringBuilder();
            httpResult = con.getResponseCode();

            if (httpResult == HttpURLConnection.HTTP_OK) {
                // Parse JSON data
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
                String line;

                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();

                String json = sb.toString().trim();

                if (json.startsWith("{")) {
                    return new JSONObject(json);
                } else if (json.startsWith("[")) {
                    return new JSONArray(json);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    @Override
    protected void onPostExecute(Object object) {
        super.onPostExecute(object);

        completionHandler.completionHandler(object, statusForCode(httpResult));
    }

    public APIResponseStatus statusForCode(int code) {
        switch (code) {
            case 200:
                return APIResponseStatus.ok;
            case 201:
                return APIResponseStatus.created;
            case 400:
                return APIResponseStatus.invalidRequest;
            case 401:
                return APIResponseStatus.unauthorized;
            case 404:
                return APIResponseStatus.notFound;
            default:
                return APIResponseStatus.offline;
        }
    }

    public interface CompletionHandler {

        void completionHandler(@Nullable Object object, APIResponseStatus status);

    }

}
