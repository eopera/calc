package com.filatov.calc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Setter
@Getter
public class AppPropertyConfiguration {
    @NestedConfigurationProperty
    private Soap soap;

    @Setter
    @Getter
    static class Soap {
        private String url;
        private String actionCallbackBaseUrl;
    }
}
