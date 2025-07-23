package org.example.momento_delivery.controllers;

import lombok.RequiredArgsConstructor;
import org.example.momento_delivery.dtos.AddToCartRequestDTO;
import org.example.momento_delivery.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody AddToCartRequestDTO dto) {
        return ResponseEntity.ok(cartService.addItem(dto));
    }


    @PutMapping("/update")
    public ResponseEntity<?> updateQuantity(@RequestParam Integer itemId,
                                            @RequestParam Integer quantity) {
        cartService.updateItemQuantity(itemId, quantity);
        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<?> remove(@PathVariable Integer itemId) {
        cartService.removeItem(itemId);
        return ResponseEntity.ok("Removed");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getItems(@PathVariable Integer userId) {
        return ResponseEntity.ok(cartService.getItems(userId));
    }

    @GetMapping("/{userId}/total")
    public ResponseEntity<?> getTotal(@PathVariable Integer userId) {
        return ResponseEntity.ok(cartService.calculateTotal(userId));
    }
}

