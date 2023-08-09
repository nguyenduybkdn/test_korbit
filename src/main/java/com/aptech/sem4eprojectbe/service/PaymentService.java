package com.aptech.sem4eprojectbe.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aptech.sem4eprojectbe.entity.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import jakarta.annotation.PostConstruct;

@Service
public class PaymentService {
    @Value("${payment.private.key}")
    private String privateKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = privateKey;
    }

    public String charge(PaymentRequest chargeRequest) {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount().multiply(new BigDecimal("100")));
        chargeParams.put("currency", "usd");
        chargeParams.put("source", chargeRequest.getToken());

        Charge charge;
        try {
            charge = Charge.create(chargeParams);
        } catch (StripeException e) {
            e.printStackTrace();
            return "fail";
        }
        return charge.getId();
    }
}
