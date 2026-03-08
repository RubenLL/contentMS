package com.poc.ruben.repository.storage.local;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.UUID;

public class LocalKeyFactory {

    public String imageKey(String originalFilename, Instant now) {
        var zdt = now.atZone(ZoneOffset.UTC);
        String yyyy = String.valueOf(zdt.getYear());
        String mm = String.format("%02d", zdt.getMonthValue());

        String safeOriginal = sanitize(originalFilename);
        String uuid = UUID.randomUUID().toString();

        return "images/%s/%s/%s-%s".formatted(yyyy, mm, uuid, safeOriginal);
    }

    private String sanitize(String name) {
        if (name == null || name.isBlank()) return "file";
        return name
                .replaceAll("\\s+", "_")
                .replaceAll("[^a-zA-Z0-9._-]", "");
    }
}