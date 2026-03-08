package com.poc.ruben.dto.image;

import java.time.LocalDate;
import java.util.List;

public record UploadImageRequest(
        String name,
        String description,
        List<String> tags,
        List<String> channels,
        LocalDate expirationDate,
        String category
) {}
