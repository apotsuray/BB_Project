package ru.bets.project.spoyer.assist;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
@Component
@PropertySource("classpath:application.properties")
public class JsonNodeBuilder {
    private final String loginSpoyer;
    private final String tokenSpoyer;
    @Autowired
    public JsonNodeBuilder(Environment env) {
        this.loginSpoyer = env.getProperty("LOGIN_SPOYER");
        this.tokenSpoyer = env.getProperty("TOKEN_SPOYER");
    }
    public JsonNode getElements(String task) throws IOException, InterruptedException {
        String apiUrl = "https://spoyer.com/api/get.php?login=" + loginSpoyer
                +"&token="+tokenSpoyer + "&" + task;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonResponse = response.body();
        JsonNode jsonNode = new ObjectMapper().readTree(jsonResponse);
        client.close();
        return jsonNode;
    }
}
