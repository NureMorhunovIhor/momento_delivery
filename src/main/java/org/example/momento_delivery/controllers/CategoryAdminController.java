package org.example.momento_delivery.controllers;

import lombok.RequiredArgsConstructor;
import org.example.momento_delivery.entities.Category;
import org.example.momento_delivery.repositories.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {
    private final CategoryRepository categoryRepo;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryRepo.save(category));
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryRepo.findAll();
    }
}

