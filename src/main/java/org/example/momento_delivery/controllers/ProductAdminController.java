package org.example.momento_delivery.controllers;

import lombok.RequiredArgsConstructor;
import org.example.momento_delivery.dtos.ProductRequestDTO;
import org.example.momento_delivery.entities.Product;
import org.example.momento_delivery.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class ProductAdminController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDTO dto) {
        Product created = productService.createProduct(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody ProductRequestDTO dto) {
        Product updated = productService.updateProduct(id, dto);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}

