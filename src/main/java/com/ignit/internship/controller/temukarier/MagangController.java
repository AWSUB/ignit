package com.ignit.internship.controller.temukarier;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.ResponseReturn;
import com.ignit.internship.dto.temukarier.MagangRequest;
import com.ignit.internship.dto.temukarier.MagangResponse;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.service.temukarier.MagangService;

@RestController
@RequestMapping("/api/temukarier/magang")
public class MagangController {

    private final MagangService magangService;

    public MagangController(MagangService magangService) {
        this.magangService = magangService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<DefaultResponse<MagangResponse>> createMagang(
        @RequestPart MultipartFile file,
        @RequestPart MagangRequest request
    ) throws Exception {
        return ResponseReturn.ok(new MagangResponse(magangService.createMagang(file, request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<MagangResponse>> getMagang(@PathVariable Long id) throws IdNotFoundException {
        return ResponseReturn.ok(new MagangResponse(magangService.getMagangById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse<MagangResponse>> updateMagang(
        @RequestBody MagangRequest request,
        @PathVariable Long id
    ) throws IdNotFoundException, IOException {
        return ResponseReturn.ok(new MagangResponse(magangService.updateMagang(request, id)));
    }   

    @GetMapping
    public ResponseEntity<DefaultResponse<List<MagangResponse>>> getMagangByTagsAndPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(required = false) List<String> tags,
        @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        if (tags == null) {
            return ResponseReturn.ok(
                magangService.getMagangByPage(pageable).stream().map(m -> new MagangResponse(m)).toList()
            );
        }
        else {
            return ResponseReturn.ok(
                magangService.getMagangByPageAndTag(pageable, tags).stream().map(m -> new MagangResponse(m)).toList()
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse<Object>> deleteMagang(@PathVariable Long id) throws IdNotFoundException {
        magangService.deleteMagang(id);
        return ResponseReturn.ok(null);
    }
}
