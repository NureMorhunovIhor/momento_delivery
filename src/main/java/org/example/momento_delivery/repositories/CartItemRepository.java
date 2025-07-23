package org.example.momento_delivery.repositories;

import org.example.momento_delivery.entities.Cart;
import org.example.momento_delivery.entities.CartItem;
import org.example.momento_delivery.entities.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCart(Cart cart);
    Optional<CartItem> findByCartAndProductVariant(Cart cart, ProductVariant variant);
}