package com.poc.ruben.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.storage")
public record StorageProperties(
        String rootDir,
        String publicBaseUrl
) {}
