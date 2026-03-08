package com.poc.ruben.repository;

import com.poc.ruben.domain.model.Asset;
import java.util.Optional;
import java.util.List;

public interface AssetRepository {
    Asset save(Asset asset);

    SearchResult searchImages(SearchQuery query);

    record SearchQuery(
            int page,
            int pageSize,
            String name,
            String description,
            List<String> tags,
            List<String> channels
    ) {}

    record SearchResult(
            List<Asset> items,
            long totalCount
    ) {}
    Optional<Asset> findActiveImageById(String id);
}




