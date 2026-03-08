package com.poc.ruben.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.mongo")
public record MongoProperties(String uri) {}