

package com.poc.ruben.repository.mongo;

import com.poc.ruben.domain.model.Asset;
import com.poc.ruben.mapper.AssetMapper;
import com.poc.ruben.repository.AssetRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public class AssetRepositoryMongoImpl implements AssetRepository {

    private final MongoAssetRepository mongo;
    private final AssetSearchMongoRepository searchRepo;

    public AssetRepositoryMongoImpl(MongoAssetRepository mongo, AssetSearchMongoRepository searchRepo) {
        this.mongo = mongo;
        this.searchRepo = searchRepo;
    }

    @Override
    public Asset save(Asset asset) {
        var saved = mongo.save(AssetMapper.toDocument(asset));
        return AssetMapper.toDomain(saved);
    }

    @Override
    public SearchResult searchImages(SearchQuery query) {
        return searchRepo.searchImages(query);
    }

@Override
public Optional<Asset> findActiveImageById(String id) {
    return searchRepo.findActiveImageById(id);
}
}