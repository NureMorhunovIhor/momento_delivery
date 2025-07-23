package org.example.momento_delivery.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product_ingredients")
public class ProductIngredient {
    @EmbeddedId
    private ProductIngredientId id = new ProductIngredientId();;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "product_id", nullable = false)
    private org.example.momento_delivery.entities.Product product;

    @MapsId("ingredientId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    public ProductIngredient(Product product, Ingredient ingredient) {
        this.product = product;
        this.ingredient = ingredient;
        this.id = new ProductIngredientId(product.getId(), ingredient.getId());
    }
}