package com.poc.ruben.repository.mongo;

import com.poc.ruben.repository.mongo.document.AssetDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoAssetRepository extends MongoRepository<AssetDocument, String> {
}