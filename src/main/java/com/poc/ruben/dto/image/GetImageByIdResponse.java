package com.poc.ruben.dto.image;

public record GetImageByIdResponse(
        String status,
        ImageDetail image
) {}