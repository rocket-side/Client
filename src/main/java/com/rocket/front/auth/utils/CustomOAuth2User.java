package com.rocket.front.auth.utils;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User, UserDetails {

    private final String username;
    private final String password;
    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;

    @Builder
    public CustomOAuth2User(String username, String password, Map<String, Object> attributes, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.attributes = attributes;
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return username;
    }

    /**
     * 계정의 만료 여부 리턴
     * @return true 만료 안됨
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정의 잠김 여부 리턴
     * @return true 잠기지 않음
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 비밀번호 만료 여부 리턴
     * @return true 만료 안됨
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정의 활성화 여부 리턴
     * @return true 활성화 됨
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
