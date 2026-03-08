package com.poc.ruben.repository.storage;

import java.util.Map;

public interface StorageRepository {
    String save(String key, byte[] bytes, String mimeType, Map<String, String> tags);
    void tag(String key, Map<String, String> tags);
}