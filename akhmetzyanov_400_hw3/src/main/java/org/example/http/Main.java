package org.example.http;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HttpService httpService = new HttpService();

        Map<String, String> getMethodParams = new HashMap<>();
        Map<String, String> getMethodHeaders = new HashMap<>();
        System.out.println(httpService.get("http://localhost:8080/hello", getMethodHeaders, getMethodParams));

        Map<String, String> postMethodData = new HashMap<>();
        postMethodData.put("name", "Bulat");
        postMethodData.put("age", "18");
        Map<String, String> postMethodHeaders = new HashMap<>();
        System.out.println(httpService.post("http://localhost:8080/hello", postMethodHeaders, postMethodData));

        Map<String, String> putMethodData = new HashMap<>();
        putMethodData.put("name", "Bulat");
        Map<String, String> putMethodHeaders = new HashMap<>();
        System.out.println(httpService.put("http://localhost:8080/hello", putMethodHeaders, putMethodData));

        Map<String, String> deleteMethodData = new HashMap<>();
        deleteMethodData.put("name", "Bulat");
        Map<String, String> deleteMethodHeaders = new HashMap<>();
        System.out.println(httpService.delete("http://localhost:8080/hello", deleteMethodHeaders, deleteMethodData));
    }
}
