package com.poc.ruben.mapper;

import com.poc.ruben.domain.model.Asset;
import com.poc.ruben.domain.model.Audit;
import com.poc.ruben.repository.mongo.document.AssetDocument;

public class AssetMapper {

    public static AssetDocument toDocument(Asset a) {
        AssetDocument d = new AssetDocument();
        d.setId(a.getId());
        d.setContentType(a.getContentType());
        d.setName(a.getName());
        d.setDescription(a.getDescription());
        d.setTags(a.getTags());
        d.setChannels(a.getChannels());
        d.setExpirationDate(a.getExpirationDate());
        d.setCategory(a.getCategory());
        d.setMimeType(a.getMimeType());
        d.setSizeBytes(a.getSizeBytes());
        d.setS3Url(a.getS3Url());

        if (a.getAudit() != null) {
            d.setCreatedBy(a.getAudit().createdBy());
            d.setCreatedAt(a.getAudit().createdAt());
            d.setUpdatedBy(a.getAudit().updatedBy());
            d.setUpdatedAt(a.getAudit().updatedAt());
            d.setDeleted(a.getAudit().deleted());
            d.setDeletedBy(a.getAudit().deletedBy());
            d.setDeletedAt(a.getAudit().deletedAt());
        }
        return d;
    }

    public static Asset toDomain(AssetDocument d) {
        Asset a = new Asset();
        a.setId(d.getId());
        a.setContentType(d.getContentType());
        a.setName(d.getName());
        a.setDescription(d.getDescription());
        a.setTags(d.getTags());
        a.setChannels(d.getChannels());
        a.setExpirationDate(d.getExpirationDate());
        a.setCategory(d.getCategory());
        a.setMimeType(d.getMimeType());
        a.setSizeBytes(d.getSizeBytes());
        a.setS3Url(d.getS3Url());

        Audit audit = new Audit(
                d.getCreatedBy(),
                d.getCreatedAt(),
                d.getUpdatedBy(),
                d.getUpdatedAt(),
                d.isDeleted(),
                d.getDeletedBy(),
                d.getDeletedAt()
        );
        a.setAudit(audit);
        return a;
    }
}