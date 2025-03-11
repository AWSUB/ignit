package com.ignit.internship.controller.temukarier;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
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
import com.ignit.internship.dto.temukarier.BootcampRequest;
import com.ignit.internship.dto.temukarier.BootcampResponse;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.service.temukarier.BootcampService;

@RestController
@RequestMapping("/api/temukarier/bootcamp")
public class BootcampController {

    private final BootcampService bootcampService;

    public BootcampController(BootcampService bootcampService) {
        this.bootcampService = bootcampService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<DefaultResponse<BootcampResponse>> createBootcamp(
        @RequestPart MultipartFile file,
        @RequestPart BootcampRequest request
    ) throws Exception {
        return ResponseReturn.ok(new BootcampResponse(bootcampService.createBootcamp(file, request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<BootcampResponse>> getBootcamp(@PathVariable Long id) throws IdNotFoundException {
        return ResponseReturn.ok(new BootcampResponse(bootcampService.getBootcampById(id)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse<BootcampResponse>> updateBootcamp(
        @RequestBody BootcampRequest request,
        @PathVariable Long id
    ) throws IdNotFoundException, IOException {
        return ResponseReturn.ok(new BootcampResponse(bootcampService.updateBootcamp(request, id)));
    }   

    @GetMapping
    public ResponseEntity<DefaultResponse<List<BootcampResponse>>> getBootcampByTagsAndPage(
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
                bootcampService.getBootcampByPage(pageable).stream().map(b -> new BootcampResponse(b)).toList()
            );
        }
        else {
            return ResponseReturn.ok(
                bootcampService.getBootcampByPageAndTag(pageable, tags).stream().map(b -> new BootcampResponse(b)).toList()
            );
        }
    }  
}
