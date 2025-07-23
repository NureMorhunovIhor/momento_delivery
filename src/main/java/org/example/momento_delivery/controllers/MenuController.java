package org.example.momento_delivery.controllers;

import org.example.momento_delivery.dtos.ProductFullDTO;
import org.example.momento_delivery.entities.Category;
import org.example.momento_delivery.entities.Product;
import org.example.momento_delivery.entities.ProductVariant;
import org.example.momento_delivery.repositories.CategoryRepository;
import org.example.momento_delivery.repositories.ProductIngredientRepository;
import org.example.momento_delivery.repositories.ProductRepository;
import org.example.momento_delivery.repositories.ProductVariantRepository;
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

    @Autowired
    private ProductVariantRepository variantRepo;

    @Autowired
    private ProductIngredientRepository productIngredientRepo;

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
    @GetMapping("/products/{id}/full")
    public ResponseEntity<?> getProductFull(@PathVariable Integer id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Продукт не знайдено"));

        ProductFullDTO dto = new ProductFullDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setBasePrice(product.getBasePrice());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);

        List<String> variants = variantRepo.findByProduct(product)
                .stream().map(v -> v.getLabel() + ": " + v.getPrice()).toList();
        dto.setVariants(variants);

        List<String> ingredients = productIngredientRepo.findByProduct(product)
                .stream().map(pi -> pi.getIngredient().getName()).toList();
        dto.setIngredients(ingredients);

        return ResponseEntity.ok(dto);
    }

}

