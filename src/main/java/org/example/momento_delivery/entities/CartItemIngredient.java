package org.example.momento_delivery.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart_item_ingredients")
public class CartItemIngredient {
    @EmbeddedId
    private CartItemIngredientId id;

    @MapsId("cartItemId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "cart_item_id", nullable = false)
    private CartItem cartItem;

    @MapsId("ingredientId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    public CartItemIngredient(CartItem item, Ingredient ing) {
        this.cartItem = item;
        this.ingredient = ing;
        this.id = new CartItemIngredientId(item.getId(), ing.getId());
    }
}