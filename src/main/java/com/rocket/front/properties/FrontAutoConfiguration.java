package com.rocket.front.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FrontProperties.class)
public class FrontAutoConfiguration {
}
