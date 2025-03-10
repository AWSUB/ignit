package com.ignit.internship.service.payment;

import java.util.Map;

import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;

import com.ignit.internship.dto.payment.PaymentNotificationRequest;
import com.ignit.internship.dto.payment.TransactionResponse;
import com.ignit.internship.exception.IdNotFoundException;
import com.ignit.internship.model.profile.UserProfile;
import com.ignit.internship.repository.profile.ProfileRepository;
import com.midtrans.Midtrans;
import com.midtrans.httpclient.SnapApi;

@Service
public class PaymentService {

    private final ProfileRepository profileRepository;

    public PaymentService(final ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public TransactionResponse createTransaction(Long profileId, Long orderId,  Long grossAmount) throws Exception {
        UserProfile profile = profileRepository.findById(profileId).orElseThrow(() -> new IdNotFoundException("Profile not found"));

        if (profile.getFullName() == null || profile.getFullName().isBlank()) {
            throw new Exception("Full name must be filled before creating transaction");
        }

        Map<String, Object> transactionRequest = Map.of(
            "transaction_details", Map.of(
                "order_id", orderId + "-" + profileId,
                "gross_amount", Long.toString(grossAmount)
            ),
            "customer_details", Map.of(
                "email", profile.getEmail()
            )
        );

        return new TransactionResponse(SnapApi.createTransactionToken(transactionRequest));
    }

    public void verifyPayment(PaymentNotificationRequest request) throws Exception {
        if (!request.getStatusCode().equals("200")) {
            throw new Exception("Payment unsuccessful");
        }

        if (request.getFraudStatus() != null && !request.getFraudStatus().equals("accept")) {
            throw new Exception("Payment denied");
        }

        if (!(request.getTransactionStatus().equals("capture") ||
            request.getTransactionStatus().equals("settlement"))
        ) {
            throw new Exception("Payment unsuccessful");
        }

        String compareSignature = Sha512DigestUtils.shaHex(request.getOrderId() + request.getStatusCode() + request.getGrossAmount() + Midtrans.getServerKey());
        
        if (!compareSignature.equals(request.getSignatureKey())) {
            throw new Exception("Payment invalid");
        }
    }
}
