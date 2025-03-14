package com.ignit.internship.controller.utils;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.ResponseReturn;
import com.ignit.internship.dto.utils.TagRequest;
import com.ignit.internship.dto.utils.TagResponse;
import com.ignit.internship.service.utils.TagService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/utils/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @Operation(description = "Create Tag")
    @PostMapping
    public ResponseEntity<DefaultResponse<TagResponse>> createTag(@RequestBody TagRequest request) {
        return ResponseReturn.ok(new TagResponse(tagService.createTag(request.getName())));
    }

    @Operation(description = "Get All Tag")
    @GetMapping
    public ResponseEntity<DefaultResponse<List<TagResponse>>> getAllTag() {
        return ResponseReturn.ok(tagService.getAllTag().stream().map(t -> new TagResponse(t)).toList());
    }

    @Operation(description = "Delete Tag by name")
    @DeleteMapping("/{name}")
    public ResponseEntity<DefaultResponse<Object>> deleteTag(@PathVariable String name) {
        tagService.deleteTag(name);
        return ResponseReturn.ok(null);
    }
}
