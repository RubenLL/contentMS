package com.poc.ruben.domain.model;

import java.time.Instant;

public record Audit(
        String createdBy,
        Instant createdAt,
        String updatedBy,
        Instant updatedAt,
        boolean deleted,
        String deletedBy,
        Instant deletedAt
) {
    public static Audit created(String userId, Instant now) {
        return new Audit(userId, now, userId, now, false, null, null);
    }
}