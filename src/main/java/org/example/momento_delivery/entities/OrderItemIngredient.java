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
@Table(name = "order_item_ingredients")
public class OrderItemIngredient {
    @EmbeddedId
    private OrderItemIngredientId id;

    @MapsId("orderItemId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @MapsId("ingredientId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    public OrderItemIngredient(OrderItem item, Ingredient ing) {
        this.orderItem = item;
        this.ingredient = ing;
        this.id = new OrderItemIngredientId(item.getId(), ing.getId());
    }
}