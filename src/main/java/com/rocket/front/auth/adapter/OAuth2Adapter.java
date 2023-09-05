package com.rocket.front.auth.adapter;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OAuth2Adapter {
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Map<String,Object>> githubEmail(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setBearerAuth(accessToken);
        httpHeaders.set("Accept","application/vnd.github+json");
//        httpHeaders.add("X-GitHub-Api-Version", "2022-11-28");

        List<Map<String,Object>> response = new ArrayList<>();

        HttpEntity<String> requestEntity = new HttpEntity<>(accessToken,httpHeaders);
        ResponseEntity<List<Map<String,Object>>> exchange = restTemplate.exchange("https://api.github.com/user/emails",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });
//        ResponseEntity<List<GithubUserEmail>> exchange = restTemplate.exchange("https://api.github.com/user/emails",
//                HttpMethod.GET,
//                requestEntity,
//                new ParameterizedTypeReference<>() {
//                });

        return exchange.getBody();
    }
}
