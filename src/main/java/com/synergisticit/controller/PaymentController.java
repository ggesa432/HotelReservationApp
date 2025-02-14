package com.synergisticit.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.synergisticit.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(
            @RequestParam String cart,
            @RequestParam int amount) {
        try {
            Session session = paymentService.createCheckoutSessionForCart(amount,
                    "http://localhost:8081/payment-success",
                    "http://localhost:8081/payment-cancel");
            Map<String, String> response = new HashMap<>();
            response.put("sessionId", session.getId());
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
