package org.example.momento_delivery.repositories;

import org.example.momento_delivery.entities.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Integer> {}
