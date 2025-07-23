package org.example.momento_delivery.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CartItemIngredientId implements java.io.Serializable {
    private static final long serialVersionUID = 871799727633019645L;
    @Column(name = "cart_item_id", nullable = false)
    private Integer cartItemId;

    @Column(name = "ingredient_id", nullable = false)
    private Integer ingredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CartItemIngredientId entity = (CartItemIngredientId) o;
        return Objects.equals(this.ingredientId, entity.ingredientId) &&
                Objects.equals(this.cartItemId, entity.cartItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, cartItemId);
    }

}