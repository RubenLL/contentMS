package com.poc.ruben.config.properties;

import com.poc.ruben.config.properties.StorageProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
@EnableConfigurationProperties(StorageProperties.class)
public class StorageWebConfig implements WebMvcConfigurer {

    private final StorageProperties props;

    public StorageWebConfig(StorageProperties props) {
        this.props = props;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String rootDir = props.rootDir();
        if (rootDir == null || rootDir.isBlank()) {
            rootDir = "./data-storage"; // default seguro
        }

        Path root = Path.of(rootDir).toAbsolutePath().normalize();
        String location = root.toUri().toString();

        registry.addResourceHandler("/files/**")
                .addResourceLocations(location)
                .setCachePeriod(3600);
    }
}