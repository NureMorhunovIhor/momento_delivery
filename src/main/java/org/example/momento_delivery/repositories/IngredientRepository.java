package org.example.momento_delivery.repositories;

import org.example.momento_delivery.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {}
