package com.poc.ruben.service.impl;

import com.poc.ruben.domain.exception.ValidationException;
import com.poc.ruben.domain.model.Asset;
import com.poc.ruben.domain.model.AssetContentType;
import com.poc.ruben.domain.model.Audit;
import com.poc.ruben.dto.image.UploadImageRequest;
import com.poc.ruben.repository.AssetRepository;
import com.poc.ruben.repository.storage.StorageRepository;
import com.poc.ruben.repository.storage.local.LocalKeyFactory;
import com.poc.ruben.service.ImageService;
import com.poc.ruben.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class ImageServiceImpl implements ImageService {

    private final AssetRepository assetRepository;
    private final StorageRepository storageRepository;
    private final LocalKeyFactory keyFactory;

    public ImageServiceImpl(
            AssetRepository assetRepository,
            StorageRepository storageRepository,
            LocalKeyFactory keyFactory
    ) {
        this.assetRepository = assetRepository;
        this.storageRepository = storageRepository;
        this.keyFactory = keyFactory;
    }

    @Override
    public UploadResult upload(String userId, UploadImageRequest payload, MultipartFile image) {
        validate(userId, payload, image);

        Instant now = Instant.now();

        Asset asset = new Asset();
        asset.setContentType(AssetContentType.IMAGE);
        asset.setName(payload.name());
        asset.setDescription(payload.description());
        asset.setTags(payload.tags());
        asset.setChannels(payload.channels());
        asset.setExpirationDate(payload.expirationDate());
        asset.setCategory(payload.category());
        asset.setMimeType(image.getContentType());
        asset.setSizeBytes(image.getSize());
        asset.setAudit(Audit.created(userId, now));

        // 1) save metadata (generates id)
        Asset saved = assetRepository.save(asset);

        // 2) save file to local storage
        String key = keyFactory.imageKey(image.getOriginalFilename(), now);
        String publicUrl;
        try {
            publicUrl = storageRepository.save(
                    key,
                    image.getBytes(),
                    image.getContentType(),
                    Map.of("deleted", "false")
            );
        } catch (Exception e) {
            throw new com.poc.ruben.domain.exception.StorageException("Failed to store image file", e);
        }

        // 3) update metadata with public url
        saved.setS3Url(publicUrl);
        Asset updated = assetRepository.save(saved);

        return new UploadResult(updated.getId(), updated.getS3Url());
    }

    private void validate(String userId, UploadImageRequest payload, MultipartFile image) {
        if (userId == null || userId.isBlank()) throw new ValidationException("X-User-Id is required");
        if (payload == null) throw new ValidationException("payload is required");
        if (payload.name() == null || payload.name().isBlank()) throw new ValidationException("payload.name is required");
        if (payload.expirationDate() == null) throw new ValidationException("payload.expirationDate is required");
        if (image == null || image.isEmpty()) throw new ValidationException("image file is required");
        if (image.getContentType() == null || image.getContentType().isBlank()) throw new ValidationException("image contentType is required");
    }

    @Override
public SearchResult search(int page, int pageSize, String name, String desc, List<String> tags, List<String> channels) {
    int safePage = Math.max(page, 0);
    int safeSize = pageSize <= 0 ? 20 : Math.min(pageSize, 200);

    var result = assetRepository.searchImages(new com.poc.ruben.repository.AssetRepository.SearchQuery(
            safePage,
            safeSize,
            name,
            desc,      // desc mapea a description
            tags,
            channels
    ));

    var items = result.items().stream().map(com.poc.ruben.mapper.ImageMapper::toListItem).toList();
    return new SearchResult(items, result.totalCount());
}
@Override
public com.poc.ruben.dto.image.ImageDetail getById(String id) {
    if (id == null || id.isBlank()) throw new com.poc.ruben.domain.exception.ValidationException("id is required");

    var asset = assetRepository.findActiveImageById(id)
            .orElseThrow(() -> new NotFoundException("Image not found"));

    return com.poc.ruben.mapper.ImageMapper.toDetail(asset);
}
}