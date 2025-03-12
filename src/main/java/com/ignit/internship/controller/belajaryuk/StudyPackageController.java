package com.ignit.internship.controller.belajaryuk;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<DefaultResponse<StudyPackageResponse>> getStudyPackage(
        @PathVariable Long id
    ) throws IdNotFoundException {
        return ResponseReturn.ok(new StudyPackageResponse(studyPackageService.getStudyPackage(id)));
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
