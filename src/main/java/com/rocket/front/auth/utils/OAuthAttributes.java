package com.rocket.front.auth.utils;

import com.rocket.front.auth.adapter.OAuth2Adapter;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;
import java.util.Objects;

@Slf4j

@Getter
public class OAuthAttributes {
    
    // TODO 수정
    private static final OAuth2Adapter oAuth2Adapter = new OAuth2Adapter();
    private final Map<String,Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String accessToken;

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
            return ofNaver(userNameAttributeName,attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao(userNameAttributeName, attributes);
        } else if ("github".equals(registrationId)) {
            return ofGithub(userNameAttributeName, attributes, accessToken);
        }
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGithub(String userNameAttributeName, Map<String,Object> attributes, String accessToken) {
        String gEmail = (String) attributes.get("email");
        if(Objects.isNull(gEmail)){
            gEmail = oAuth2Adapter.githubEmail(accessToken).stream()
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
                .name((String) response.get("nickname"))
                .email((String) response.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String,Object> attributes) {

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProperties = (Map<String, Object>) attributes.get("properties");

        return OAuthAttributes.builder()
                .name((String) kakaoProperties.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .attributes(attributes)
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
