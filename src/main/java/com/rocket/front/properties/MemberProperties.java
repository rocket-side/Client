package com.rocket.front.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "member")
public class MemberProperties {
    private String host = "13.209.185.140";
    private Integer port = 8888;
}
