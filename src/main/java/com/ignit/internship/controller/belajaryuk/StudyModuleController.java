package com.ignit.internship.controller.belajaryuk;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.dto.ResponseReturn;
import com.ignit.internship.dto.belajaryuk.StudyModuleRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.service.belajaryuk.StudyModuleService;


@RestController
@RequestMapping("/api/belajaryuk/modules")
public class StudyModuleController {

    private final StudyModuleService studyModuleService;

    public StudyModuleController(StudyModuleService studyModuleService) {
        this.studyModuleService = studyModuleService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> createStudyModule(
        @RequestParam MultipartFile file,
        @RequestParam StudyModuleRequest request
    ) throws Exception {
        return ResponseReturn.ok(studyModuleService.createStudyModule(file, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudyModule(@PathVariable Long id) throws IdNotFoundException {
        return ResponseReturn.ok(studyModuleService.getStudyModuleById(id));
    }
}
