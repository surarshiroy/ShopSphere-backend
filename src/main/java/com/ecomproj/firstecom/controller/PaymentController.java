package com.ecomproj.firstecom.controller;

import com.ecomproj.firstecom.dto.PaymentRequest;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @PostMapping("/create-order")
    public String createOrder(
            @RequestBody PaymentRequest request)
            throws Exception {

        RazorpayClient client =
                new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest =
                new JSONObject();

        orderRequest.put(
                "amount",
                request.getAmount() * 100
        );

        orderRequest.put(
                "currency",
                "INR"
        );

        orderRequest.put(
                "receipt",
                "receipt_" + System.currentTimeMillis()
        );

        Order order =
                client.orders.create(orderRequest);

        return order.toString();
    }
}
