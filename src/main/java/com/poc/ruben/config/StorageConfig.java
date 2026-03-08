package com.poc.ruben.config;

import com.poc.ruben.config.properties.StorageProperties;
import com.poc.ruben.repository.storage.StorageRepository;
import com.poc.ruben.repository.storage.local.LocalKeyFactory;
import com.poc.ruben.repository.storage.local.LocalStorageRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {

    @Bean
    public LocalKeyFactory localKeyFactory() {
        return new LocalKeyFactory();
    }

    @Bean
    public StorageRepository storageRepository(StorageProperties props) {
        return new LocalStorageRepository(props);
    }
}