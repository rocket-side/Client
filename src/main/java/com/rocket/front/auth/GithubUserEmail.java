package com.rocket.front.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GithubUserEmail {
    private String email;
    private Boolean verified;
    private Boolean primary;
    private String visibility;
}
