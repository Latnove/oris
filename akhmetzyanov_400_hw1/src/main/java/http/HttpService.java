package http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpService implements HttpClient {
    public String get(String urlName, Map<String, String> headers, Map<String, String> params) {
        StringBuilder str = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            str.append("?");

            for (String key : params.keySet()) {
                if (str.length() > 1) str.append("&");
                str.append(key).append("=").append(params.get(key));
            }
        }

        try {
            URL url = new URL(urlName + str);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (headers != null) {
                for (String key : headers.keySet()) {
                    connection.setRequestProperty(key, headers.get(key));
                }
            }
            connection.setRequestProperty("Content-Type", "application/json");

            String res = readResponse(connection);
            connection.disconnect();

            return res;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String post(String urlName, Map<String, String> headers, Map<String, String> data) {
        try {
            URL url = new URL(urlName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    connection.setRequestProperty(key, headers.get(key));
                }
            }
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{");
            int size = data.size();
            int i = 0;
            for (String key : data.keySet()) {
                jsonBuilder.append("\"").append(key).append("\":");
                jsonBuilder.append("\"").append(data.get(key)).append("\"");
                if (i < size - 1) {
                    jsonBuilder.append(",");
                }
                i++;
            }
            jsonBuilder.append("}");

            String jsonInput = jsonBuilder.toString();
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }
            String res = readResponse(connection);
            connection.disconnect();

            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String put(String urlName, Map<String, String> headers, Map<String, String> data) {
        try {
            URL url = new URL(urlName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    connection.setRequestProperty(key, headers.get(key));
                }
            }
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{");
            int size = data.size();
            int i = 0;
            for (String key : data.keySet()) {
                jsonBuilder.append("\"").append(key).append("\":");
                jsonBuilder.append("\"").append(data.get(key)).append("\"");
                if (i < size - 1) {
                    jsonBuilder.append(",");
                }
                i++;
            }
            jsonBuilder.append("}");

            String jsonInput = jsonBuilder.toString();
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }
            String res = readResponse(connection);
            connection.disconnect();

            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String delete(String urlName, Map<String, String> headers, Map<String, String> data) {
        try {
            URL url = new URL(urlName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    connection.setRequestProperty(key, headers.get(key));
                }
            }
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            StringBuilder jsonBuilder = new StringBuilder();
            jsonBuilder.append("{");
            int size = data.size();
            int i = 0;
            for (String key : data.keySet()) {
                jsonBuilder.append("\"").append(key).append("\":");
                jsonBuilder.append("\"").append(data.get(key)).append("\"");
                if (i < size - 1) {
                    jsonBuilder.append(",");
                }
                i++;
            }
            jsonBuilder.append("}");

            String jsonInput = jsonBuilder.toString();
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                outputStream.write(input, 0, input.length);
            }
            String res = readResponse(connection);
            connection.disconnect();

            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String readResponse(HttpURLConnection connection) throws IOException {
        InputStream stream;
        if (connection.getResponseCode() >= 400) {
            stream = connection.getErrorStream();
        } else {
            stream = connection.getInputStream();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }
}

