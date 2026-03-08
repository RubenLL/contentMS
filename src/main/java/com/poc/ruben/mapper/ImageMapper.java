package com.poc.ruben.mapper;

import com.poc.ruben.domain.model.Asset;
import com.poc.ruben.dto.image.ImageDetail;
import com.poc.ruben.dto.image.ImageListItem;

public class ImageMapper {

    public static ImageListItem toListItem(Asset a) {
        return new ImageListItem(
                a.getId(),
                a.getName(),
                a.getDescription(),
                a.getTags(),
                a.getChannels(),
                a.getS3Url()
        );
    }

    public static ImageDetail toDetail(Asset a) {
        return new ImageDetail(
                a.getId(),
                a.getName(),
                a.getDescription(),
                a.getTags(),
                a.getChannels(),
                a.getCategory(),
                a.getExpirationDate(),
                a.getS3Url()
        );
    }
}