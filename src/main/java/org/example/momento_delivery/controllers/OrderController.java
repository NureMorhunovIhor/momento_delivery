package org.example.momento_delivery.controllers;

import lombok.RequiredArgsConstructor;
import org.example.momento_delivery.dtos.CreateOrderRequestDTO;
import org.example.momento_delivery.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody CreateOrderRequestDTO dto) {
        return ResponseEntity.ok(orderService.placeOrder(dto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestParam String status) {
        orderService.changeStatus(id, status);
        return ResponseEntity.ok("Updated");
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserOrders(@PathVariable Integer userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @GetMapping("/test-mail")
    public ResponseEntity<?> testMail() {
        orderService.sendTestMail("lapaspalma@gmail.com");
        return ResponseEntity.ok("Письмо отправлено!");
    }
}
