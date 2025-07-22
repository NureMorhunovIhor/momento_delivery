package org.example.momento_delivery.repositories;

import org.example.momento_delivery.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {}

