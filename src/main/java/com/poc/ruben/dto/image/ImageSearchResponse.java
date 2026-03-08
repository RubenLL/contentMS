package com.poc.ruben.dto.image;

import java.util.List;

public record ImageSearchResponse(
        String status,
        List<ImageListItem> images
) {}