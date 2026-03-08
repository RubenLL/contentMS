package com.poc.ruben.dto.image;

import java.util.List;

public record ImageListItem(
        String id,
        String name,
        String description,
        List<String> tags,
        List<String> channels,
        String s3Url
) {}