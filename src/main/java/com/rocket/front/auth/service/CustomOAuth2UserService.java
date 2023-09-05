package com.rocket.front.auth.service;


import com.rocket.front.auth.utils.CustomOAuth2User;
import com.rocket.front.auth.utils.OAuthAttributes;
import com.rocket.front.member.adapter.MemberAdapter;
import com.rocket.front.member.domain.response.MemberLoginInfoResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberAdapter memberAdapter;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        OAuth2AccessToken oAuth2AccessToken = userRequest.getAccessToken();

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes(),userRequest.getAccessToken().getTokenValue());
        log.info("::::email:::: {}",attributes.getAttributes());
        log.info("::::pk:::: {}",attributes.getNameAttributeKey());
        log.info("::::access_token:::: {}",userRequest.getAccessToken().getTokenValue());
        log.info("::::access_token:::: {}",userRequest.getAccessToken().getTokenType().getValue());



        log.info("::::oAuth2User {}", (Object) oAuth2User.getAttribute("access_token"));

        MemberLoginInfoResponseDto member = getUser(attributes);

        return CustomOAuth2User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .authorities(Collections.singleton(new SimpleGrantedAuthority(member.getRoleResponseDto().getName())))
                .build();
    }

    private MemberLoginInfoResponseDto getUser(OAuthAttributes attributes) {
        return memberAdapter.getSignInMemberInfoByEmail(attributes.getEmail());
    }


}
