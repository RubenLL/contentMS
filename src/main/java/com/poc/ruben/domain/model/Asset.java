package com.poc.ruben.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Asset {
    private String id;
    private AssetContentType contentType;

    private String name;
    private String description;
    private List<String> tags;
    private List<String> channels;

    private LocalDate expirationDate;
    private String category;

    private String mimeType;
    private long sizeBytes;

    private String s3Url; // URL publica completa (local /files/...)

    private Audit audit;

    // getters/setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public AssetContentType getContentType() { return contentType; }
    public void setContentType(AssetContentType contentType) { this.contentType = contentType; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public List<String> getChannels() { return channels; }
    public void setChannels(List<String> channels) { this.channels = channels; }

    public LocalDate getExpirationDate() { return expirationDate; }
    public void setExpirationDate(LocalDate expirationDate) { this.expirationDate = expirationDate; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }

    public long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; }

    public String getS3Url() { return s3Url; }
    public void setS3Url(String s3Url) { this.s3Url = s3Url; }

    public Audit getAudit() { return audit; }
    public void setAudit(Audit audit) { this.audit = audit; }
}