package org.example.momento_delivery.controllers;

import org.example.momento_delivery.entities.Category;
import org.example.momento_delivery.entities.Product;
import org.example.momento_delivery.repositories.CategoryRepository;
import org.example.momento_delivery.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @GetMapping("/products/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Integer categoryId) {
        List<Product> products = productRepo.findByCategoryId(categoryId);

        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found");
        }

        return ResponseEntity.ok(products);
    }


    @GetMapping("/products/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productRepo.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductDetails(@PathVariable Integer id) {
        return productRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

