package org.example.momento_delivery.repositories;

import org.example.momento_delivery.entities.Product;
import org.example.momento_delivery.entities.ProductIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductIngredientRepository extends JpaRepository<ProductIngredient, Integer> {
    List<ProductIngredient> findByProduct(Product product);
    void deleteAllByProduct(Product product);
}
