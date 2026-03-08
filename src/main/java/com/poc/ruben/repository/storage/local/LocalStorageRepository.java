package com.poc.ruben.repository.storage.local;

import com.poc.ruben.config.properties.StorageProperties;
import com.poc.ruben.domain.exception.StorageException;
import com.poc.ruben.repository.storage.StorageRepository;

import java.io.IOException;
import java.nio.file.*;
import java.util.Map;

public class LocalStorageRepository implements StorageRepository {

    private final StorageProperties props;

    public LocalStorageRepository(StorageProperties props) {
        this.props = props;
    }

    @Override
    public String save(String key, byte[] bytes, String mimeType, Map<String, String> tags) {
        try {
            Path root = Path.of(props.rootDir()).toAbsolutePath().normalize();
            Path filePath = root.resolve(key).normalize();

            if (!filePath.startsWith(root)) {
                throw new StorageException("Invalid storage key (path traversal attempt)");
            }

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            if (tags != null && !tags.isEmpty()) {
                writeMeta(filePath, tags);
            }

            return buildPublicUrl(key);

        } catch (IOException e) {
            throw new StorageException("Local storage write failed", e);
        }
    }

    @Override
    public void tag(String key, Map<String, String> tags) {
        try {
            Path root = Path.of(props.rootDir()).toAbsolutePath().normalize();
            Path filePath = root.resolve(key).normalize();

            if (!filePath.startsWith(root)) {
                throw new StorageException("Invalid storage key (path traversal attempt)");
            }
            if (!Files.exists(filePath)) {
                throw new StorageException("File not found to tag: " + key);
            }

            writeMeta(filePath, tags);

        } catch (IOException e) {
            throw new StorageException("Local storage tagging failed", e);
        }
    }

    private void writeMeta(Path filePath, Map<String, String> tags) throws IOException {
        // Simple: sidecar file "original.ext.meta"
        Path metaPath = Path.of(filePath.toString() + ".meta");
        String content = tags.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .reduce("", (a, b) -> a.isEmpty() ? b : a + "\n" + b);

        Files.writeString(metaPath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private String buildPublicUrl(String key) {
        String base = props.publicBaseUrl();
        if (base.endsWith("/")) return base + key;
        return base + "/" + key;
    }
}