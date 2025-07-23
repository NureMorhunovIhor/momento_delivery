package org.example.momento_delivery.repositories;


import org.example.momento_delivery.entities.OrderItemIngredient;
import org.example.momento_delivery.entities.OrderItemIngredientId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemIngredientRepository extends JpaRepository<OrderItemIngredient, OrderItemIngredientId> {
}

