package org.example.momento_delivery.repositories;

import org.example.momento_delivery.entities.CartItem;
import org.example.momento_delivery.entities.CartItemIngredient;
import org.example.momento_delivery.entities.CartItemIngredientId;
import org.example.momento_delivery.entities.Ingredient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemIngredientRepository extends JpaRepository<CartItemIngredient, CartItemIngredientId> {
    @Query("SELECT c.ingredient FROM CartItemIngredient c WHERE c.cartItem = :cartItem")
    List<Ingredient> findIngredientsByCartItem(@Param("cartItem") CartItem cartItem);

}