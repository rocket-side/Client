package com.rocket.front.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "project")
public class ProjectProperties {

    private String host = "localhost";

    private Integer port = 8989;
}
