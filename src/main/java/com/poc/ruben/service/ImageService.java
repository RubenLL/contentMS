package com.poc.ruben.service;

import com.poc.ruben.dto.image.ImageDetail;
import com.poc.ruben.dto.image.ImageListItem;

import java.util.List;

public interface ImageService {
    UploadResult upload(String userId, com.poc.ruben.dto.image.UploadImageRequest payload,
                        org.springframework.web.multipart.MultipartFile image);

    SearchResult search(int page, int pageSize, String name, String desc, List<String> tags, List<String> channels);

    record UploadResult(String id, String publicUrl) {}
    record SearchResult(List<ImageListItem> items, long totalCount) {}
    ImageDetail getById(String id);
}