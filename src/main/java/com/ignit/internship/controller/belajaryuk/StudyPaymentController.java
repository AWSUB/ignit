package com.ignit.internship.controller.belajaryuk;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ignit.internship.dto.DefaultResponse;
import com.ignit.internship.dto.ResponseReturn;
import com.ignit.internship.dto.payment.PaymentNotificationRequest;
import com.ignit.internship.dto.payment.TransactionResponse;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.auth.User;
import com.ignit.internship.service.belajaryuk.StudyPackageService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/belajaryuk/payments")
public class StudyPaymentController {

    private final StudyPackageService studyPackageService;

    public StudyPaymentController(final StudyPackageService studyPackageService) {
        this.studyPackageService = studyPackageService;
    }

    @Operation(description = "Create Transaction by Study Package id, token to be used by front-end using snap.js")
    @GetMapping("/transaction/{packageId}")
    public ResponseEntity<DefaultResponse<TransactionResponse>> createTransaction(
        @PathVariable Long packageId,
        @CurrentSecurityContext SecurityContext context
    ) throws Exception {
        User user = (User) context.getAuthentication().getPrincipal();
        return ResponseReturn.ok(studyPackageService.createStudyPackageTransaction(user.getId(), packageId));
    }

    @Operation(description = "Verify and process payment by Midtrans, used only by Midtrans")
    @PostMapping("/verify")
    public ResponseEntity<DefaultResponse<Object>> verifyPayment(@RequestBody PaymentNotificationRequest request) throws IllegalArgumentException, IdNotFoundException {
        studyPackageService.processStudyPackagePayment(request);
        return ResponseReturn.ok(null);
    }
}
