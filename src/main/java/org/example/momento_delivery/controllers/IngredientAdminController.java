package org.example.momento_delivery.controllers;

import lombok.RequiredArgsConstructor;
import org.example.momento_delivery.entities.Ingredient;
import org.example.momento_delivery.repositories.IngredientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
@RequiredArgsConstructor
public class IngredientAdminController {
    private final IngredientRepository ingredientRepo;

    @PostMapping
    public ResponseEntity<?> createIngredient(@RequestBody Ingredient ingredient) {
        return ResponseEntity.ok(ingredientRepo.save(ingredient));
    }

    @GetMapping
    public List<Ingredient> getAll() {
        return ingredientRepo.findAll();
    }
}

