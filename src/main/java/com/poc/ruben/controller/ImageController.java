package com.poc.ruben.controller;

import com.poc.ruben.dto.image.UploadImageRequest;
import com.poc.ruben.dto.image.UploadImageResponse;
import com.poc.ruben.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<UploadImageResponse> upload(
            @RequestHeader("X-User-Id") String userId,
            @RequestPart("payload") UploadImageRequest payload,
            @RequestPart("image") MultipartFile image
    ) {
        var result = imageService.upload(userId, payload, image);
        return ResponseEntity.status(201)
                .body(new UploadImageResponse("OK", result.id(), result.publicUrl()));
    }
    @GetMapping
    public ResponseEntity<com.poc.ruben.dto.image.ImageSearchResponse> search(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int pageSize,
        @RequestParam(required = false) String name,
        @RequestParam(required = false, name = "desc") String desc,
        @RequestParam(required = false) String tags,
        @RequestParam(required = false) String channels
    ) {
    var tagList = splitCsv(tags);
    var channelList = splitCsv(channels);

    var result = imageService.search(page, pageSize, name, desc, tagList, channelList);

    return ResponseEntity.ok()
            .header("count", String.valueOf(result.totalCount()))
            .header("page", String.valueOf(Math.max(page, 0)))
            .header("pageSize", String.valueOf(pageSize <= 0 ? 20 : Math.min(pageSize, 200)))
            .body(new com.poc.ruben.dto.image.ImageSearchResponse("OK", result.items()));
}

    private java.util.List<String> splitCsv(String value) {
        if (value == null || value.isBlank()) return java.util.List.of();
        return java.util.Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .distinct()
                .toList();
    }
    @GetMapping("/{id}")
    public ResponseEntity<com.poc.ruben.dto.image.GetImageByIdResponse> getById(@PathVariable String id) {
        var detail = imageService.getById(id);
        return ResponseEntity.ok(new com.poc.ruben.dto.image.GetImageByIdResponse("OK", detail));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @RequestHeader("X-User-Id") String userId,
            @PathVariable String id
    ) {
        imageService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }   

}