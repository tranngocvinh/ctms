package com.ctms.controller;

import com.ctms.entity.QrRequest;
import com.ctms.service.PayOSPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.payos.type.CheckoutResponseData;

@RestController
public class PaymentController {

    @Autowired
    private PayOSPaymentService payOSPaymentService;

    @PostMapping("/v2/payment-requests")
    public CheckoutResponseData initiatePayment(@RequestBody QrRequest qrRequest) throws Exception {
       return payOSPaymentService.createPaymentRequest(qrRequest) ;

    }
}
