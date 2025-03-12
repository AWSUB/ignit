package com.ignit.internship.controller.belajaryuk;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
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
import com.ignit.internship.dto.belajaryuk.StudyPackageRequest;
import com.ignit.internship.dto.belajaryuk.StudyPackageResponse;
import com.ignit.internship.dto.payment.PaymentNotificationRequest;
import com.ignit.internship.dto.payment.TransactionResponse;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.service.belajaryuk.StudyPackageService;
import com.ignit.internship.service.payment.PaymentService;

@RestController
@RequestMapping("/api/belajaryuk/packages")
public class StudyPackageController {

    private final StudyPackageService studyPackageService;

    private final PaymentService paymentService;

    public StudyPackageController(
        final StudyPackageService studyPackageService,
        final PaymentService paymentService
    ) {
        this.studyPackageService = studyPackageService;
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<DefaultResponse<StudyPackageResponse>> createStudyPackage(
        @RequestPart MultipartFile file,
        @RequestPart StudyPackageRequest request
    ) throws Exception {
        return ResponseReturn.ok(new StudyPackageResponse(studyPackageService.createStudyPackage(file, request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DefaultResponse<StudyPackageResponse>> getStudyPackageById(
        @PathVariable Long id
    ) throws IdNotFoundException {
        return ResponseReturn.ok(new StudyPackageResponse(studyPackageService.getStudyPackageById(id)));
    }

    @GetMapping
    public ResponseEntity<DefaultResponse<List<StudyPackageResponse>>> getStudyPackageByTagAndPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "3") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(required = false) String tag,
        @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        if (tag == null) {
            return ResponseReturn.ok(
                studyPackageService.getStudyPackageByPage(pageable).stream().map(sp -> new StudyPackageResponse(sp)).toList()
            );
        }
        else {
            return ResponseReturn.ok(
                studyPackageService.getStudyPackageByPageAndTag(pageable, tag).stream().map(sp -> new StudyPackageResponse(sp)).toList()
            );
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DefaultResponse<StudyPackageResponse>> updateStudyPackage(
        @PathVariable Long id,
        @RequestBody StudyPackageRequest request
    ) throws IdNotFoundException {
        return ResponseReturn.ok(new StudyPackageResponse(studyPackageService.updateStudyPackage(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse<Object>> deleteStudyPackage(@PathVariable Long id) throws IdNotFoundException {
        studyPackageService.deleteStudyPackage(id);
        return ResponseReturn.ok(null);
    }

    @GetMapping("/{id}/transaction")
    public ResponseEntity<DefaultResponse<TransactionResponse>> createTransaction(
        @PathVariable Long id,
        @CurrentSecurityContext SecurityContext context
    ) throws Exception {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(studyPackageService.createStudyPackageTransaction(user.getId(), id));
    }
    
    @PostMapping("/verify")
    public ResponseEntity<DefaultResponse<Object>> verifyPayment(@RequestBody PaymentNotificationRequest request) {
        paymentService.verifyPayment(request);
        return ResponseReturn.ok(null);
    }
}
