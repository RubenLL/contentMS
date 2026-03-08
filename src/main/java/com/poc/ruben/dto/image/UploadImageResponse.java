package com.poc.ruben.dto.image;

public record UploadImageResponse(
        String status,
        String id,
        String s3UrlImage
) {}