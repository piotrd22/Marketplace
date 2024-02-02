package com.example.marketplace.controllers;

import com.example.marketplace.enums.PaymentMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/enums")
public class EnumController extends AbstractControllerBase {

    @GetMapping("/payment-methods")
    public List<PaymentMethod> getAllPaymentMethods() {
        logger.info("Inside: EnumController -> getAllPaymentMethods()...");
        return List.of(PaymentMethod.values());
    }
}
