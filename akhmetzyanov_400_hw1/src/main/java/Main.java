import http.HttpClient;
import http.HttpService;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        HttpService httpService = new HttpService();

        // GET METHOD
        Map<String, String> getMethodParams = new HashMap<>();
        getMethodParams.put("userId", "1");
        Map<String, String> getMethodHeaders = new HashMap<>();
        System.out.println(httpService.get("https://jsonplaceholder.typicode.com/posts", getMethodHeaders, getMethodParams));

        // POST METHOD
        Map<String, String> postMethodData = new HashMap<>();
        postMethodData.put("email", "test@example.com");
        postMethodData.put("name", "Иван Иванов");
        postMethodData.put("gender", "male");
        postMethodData.put("status", "active");
        Map<String, String> postMethodHeaders = new HashMap<>();
        postMethodHeaders.put("Authorization", "Bearer TOKEN");
        System.out.println(httpService.post("https://gorest.co.in/public/v2/users/", postMethodHeaders, postMethodData));

        // PUT METHOD
        Map<String, String> putMethodData = new HashMap<>();
        putMethodData.put("name", "BULAT");
        Map<String, String> putMethodHeaders = new HashMap<>();
        putMethodHeaders.put("Authorization", "Bearer TOKEN");
        System.out.println(httpService.put("https://gorest.co.in/public/v2/users/userId", putMethodHeaders, putMethodData));

        // DELETE METHOD
        Map<String, String> deleteMethodData = new HashMap<>();
        Map<String, String> deleteMethodHeaders = new HashMap<>();
        deleteMethodHeaders.put("Authorization", "Bearer TOKEN");
        System.out.println(httpService.delete("https://gorest.co.in/public/v2/users/userId", deleteMethodHeaders, deleteMethodData));
    }
}
