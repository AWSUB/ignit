package com.ignit.internship.controller.utils;

import java.io.IOException;
import java.net.URI;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.ResponseReturn;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.utils.Image;
import com.ignit.internship.service.utils.ImageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/utils/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @Operation(description = "Upload Image to database")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<DefaultResponse<Object>> uploadImage(
        @RequestPart MultipartFile file
    ) throws Exception {
        Image image = imageService.uploadImage(file);
        return ResponseReturn.created(URI.create("/image/" + image.getId()), null);
    }

    @Operation(description = "Get Image by id")
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) throws IdNotFoundException {
        Image image = imageService.getImage(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf(image.getType())).body(image.getData());
    }

    @Operation(description = "Update Image by id")
    @PatchMapping(path = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<DefaultResponse<Object>> updateImage(
        @PathVariable Long id,
        @RequestPart MultipartFile file
    ) throws IdNotFoundException, IOException {
        imageService.updateImage(file, id);
        return ResponseReturn.ok(null);
    }

    @Operation(description = "Delete Image by id")
    @DeleteMapping("/(id)")
    public ResponseEntity<DefaultResponse<Object>> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseReturn.ok(null);
    }
}
