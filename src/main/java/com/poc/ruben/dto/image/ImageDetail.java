package com.poc.ruben.dto.image;

import java.time.LocalDate;
import java.util.List;

public record ImageDetail(
        String id,
        String name,
        String description,
        List<String> tags,
        List<String> channels,
        String category,
        LocalDate expirationDate,
        String s3Url
) {}