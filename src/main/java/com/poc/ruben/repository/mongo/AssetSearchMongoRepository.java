package com.poc.ruben.repository.mongo;

import com.poc.ruben.domain.model.Asset;
import com.poc.ruben.domain.model.AssetContentType;
import com.poc.ruben.mapper.AssetMapper;
import com.poc.ruben.repository.AssetRepository;
import com.poc.ruben.repository.mongo.document.AssetDocument;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public class AssetSearchMongoRepository {

    private final MongoTemplate mongoTemplate;

    public AssetSearchMongoRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public AssetRepository.SearchResult searchImages(AssetRepository.SearchQuery q) {
        Criteria criteria = new Criteria().andOperator(
                Criteria.where("contentType").is(AssetContentType.IMAGE),
                Criteria.where("deleted").is(false),
                Criteria.where("expirationDate").gt(LocalDate.now())
        );

        // name: contains (case-insensitive)
        if (q.name() != null && !q.name().isBlank()) {
            criteria = new Criteria().andOperator(criteria,
                    Criteria.where("name").regex(Patterns.containsIgnoreCase(q.name()))
            );
        }

        // description: contains (case-insensitive)
        if (q.description() != null && !q.description().isBlank()) {
            criteria = new Criteria().andOperator(criteria,
                    Criteria.where("description").regex(Patterns.containsIgnoreCase(q.description()))
            );
        }

        // tags ANY (OR): document.tags contains any
        if (q.tags() != null && !q.tags().isEmpty()) {
            criteria = new Criteria().andOperator(criteria,
                    Criteria.where("tags").in(q.tags())
            );
        }

        // channels ANY (OR): document.channels contains any
        if (q.channels() != null && !q.channels().isEmpty()) {
            criteria = new Criteria().andOperator(criteria,
                    Criteria.where("channels").in(q.channels())
            );
        }

        Query query = new Query(criteria)
                .with(Sort.by(Sort.Direction.ASC, "name"))
                .skip((long) q.page() * q.pageSize())
                .limit(q.pageSize());

        List<AssetDocument> docs = mongoTemplate.find(query, AssetDocument.class);

        // totalCount: misma criteria, sin skip/limit
        long total = mongoTemplate.count(new Query(criteria), AssetDocument.class);

        List<Asset> items = docs.stream().map(AssetMapper::toDomain).toList();
        return new AssetRepository.SearchResult(items, total);
    }

    /** Helpers para regex */
    private static final class Patterns {
        static String containsIgnoreCase(String input) {
            String escaped = java.util.regex.Pattern.quote(input.trim());
            return ".*" + escaped + ".*";
        }
    }

    public Optional<Asset> findActiveImageById(String id) {
    Criteria criteria = new Criteria().andOperator(
            Criteria.where("_id").is(id),
            Criteria.where("contentType").is(AssetContentType.IMAGE),
            Criteria.where("deleted").is(false),
            Criteria.where("expirationDate").gt(LocalDate.now())
    );

    Query q = new Query(criteria);
    AssetDocument doc = mongoTemplate.findOne(q, AssetDocument.class);
    if (doc == null) return Optional.empty();
    return Optional.of(AssetMapper.toDomain(doc));
}


public Optional<Asset> findNotDeletedImageById(String id) {
    Criteria criteria = new Criteria().andOperator(
            Criteria.where("_id").is(id),
            Criteria.where("contentType").is(AssetContentType.IMAGE),
            Criteria.where("deleted").is(false)
    );

    Query q = new Query(criteria);
    AssetDocument doc = mongoTemplate.findOne(q, AssetDocument.class);
    if (doc == null) return Optional.empty();
    return Optional.of(AssetMapper.toDomain(doc));
}
}