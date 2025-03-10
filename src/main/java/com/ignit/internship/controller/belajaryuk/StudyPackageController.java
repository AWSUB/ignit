package com.ignit.internship.controller.belajaryuk;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ignit.internship.dto.ResponseReturn;
import com.ignit.internship.dto.belajaryuk.StudyPackageRequest;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.service.belajaryuk.StudyPackageService;

@RestController
@RequestMapping("/api/belajaryuk/packages")
public class StudyPackageController {

    private final StudyPackageService studyPackageService;

    public StudyPackageController(StudyPackageService studyPackageService) {
        this.studyPackageService = studyPackageService;
    }

    @PostMapping
    public ResponseEntity<?> createStudyPackage(
        @RequestPart MultipartFile file,
        @RequestPart StudyPackageRequest request
    ) throws Exception {
        return ResponseReturn.ok(studyPackageService.createStudyPackage(file, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudyPackage(
        @PathVariable Long id
    ) throws IdNotFoundException {
        return ResponseReturn.ok(studyPackageService.getStudyPackage(id));
    }

    @GetMapping("/{id}/transaction")
    public ResponseEntity<?> createTransaction(
        @PathVariable Long id,
        @CurrentSecurityContext SecurityContext context
    ) throws Exception {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(studyPackageService.createTransaction(user.getId(), id));
    }
    
}
