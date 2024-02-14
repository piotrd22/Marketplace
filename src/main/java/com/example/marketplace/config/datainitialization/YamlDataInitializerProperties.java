package com.example.marketplace.config.datainitialization;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = YamlDataInitializerProperties.PREFIX)
@PropertySource(value = "classpath:datainitializer.yml", factory = YamlPropertySourceFactory.class)
public class YamlDataInitializerProperties {
    public static final String PREFIX = "data";
    private Map<String, List<Map<String, String>>> categories;
}
