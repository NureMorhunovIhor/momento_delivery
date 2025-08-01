package org.example.momento_delivery.repositories;

import org.example.momento_delivery.entities.Product;
import org.example.momento_delivery.entities.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {
    List<ProductVariant> findByProduct(Product product);
    void deleteAllByProduct(Product product);
}
