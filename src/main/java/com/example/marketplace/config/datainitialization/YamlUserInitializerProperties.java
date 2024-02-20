package com.example.marketplace.config.datainitialization;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = YamlUserInitializerProperties.PREFIX)
public class YamlUserInitializerProperties {
    public static final String PREFIX = "initializer.user";
    private String username;
    private String email;
    private String password;
}
