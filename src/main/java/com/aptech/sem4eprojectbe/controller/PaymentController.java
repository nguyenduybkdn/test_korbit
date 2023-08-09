package com.aptech.sem4eprojectbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aptech.sem4eprojectbe.common.model.ResponseModel;
import com.aptech.sem4eprojectbe.entity.PaymentRequest;
import com.aptech.sem4eprojectbe.service.PaymentService;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseModel completePayment(@RequestBody PaymentRequest request) {
        String chargeId = paymentService.charge(request);

        if (chargeId.equals("fail")) {
            return new ResponseModel("fail", "Payment fail", null);
        } else {
            return new ResponseModel("ok", "success", chargeId);
        }
    }

}
