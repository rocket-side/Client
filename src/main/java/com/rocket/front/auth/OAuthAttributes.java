package com.rocket.front.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Getter
public class OAuthAttributes {

    private Map<String,Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String accessToken;

    @Builder
    public OAuthAttributes(Map<String,Object> attributes, String nameAttributeKey, String name, String email, String accessToken) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.accessToken = accessToken;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String,Object> attributes, String accessToken) {
        if("naver".equals(registrationId)) {
            return ofNaver("id",attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        } else if ("github".equals(registrationId)) {
            return ofGithub(userNameAttributeName, attributes, accessToken);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGithub(String userNameAttributeName, Map<String,Object> attributes, String accessToken) {
        String gEmail = (String) attributes.get("email");
        if(Objects.isNull(gEmail)){
            gEmail = githubEmail(accessToken).stream()
                    .filter(userInfo -> (boolean) userInfo.get("primary").equals(true))
                    .map(userInfo -> (String) userInfo.get("email"))
                    .findFirst()
                    .orElseThrow(() -> new UsernameNotFoundException("github login failure"));
//            gEmail = githubEmail(accessToken).stream()
//                    .filter(userInfo -> userInfo.getPrimary().equals(true))
//                    .map(GithubUserEmail::getEmail)
//                    .findFirst()
//                    .orElseThrow(() -> new UsernameNotFoundException("github 로그인 실패"));

            log.info("::::gEmail:::::{}",gEmail);
        }

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email(gEmail)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static List<Map<String,Object>> githubEmail(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

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

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String,Object> attributes) {

        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }
    
    /*
        카카오와 네이버는 응답이 body에 감싸져서 들어옴
     */
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String,Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("id");

        return OAuthAttributes.builder()
                .name((String) response.get("profile_nickname"))
                .email((String) response.get("account_email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


//    public User toEntity(){
//        return User.builder()
//                .name(name)
//                .email(email)
//                .picture(picture)
//                .role(Role.USER)
//                .build();
//    }


}
