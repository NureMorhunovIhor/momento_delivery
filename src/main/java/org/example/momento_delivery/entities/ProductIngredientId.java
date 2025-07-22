package org.example.momento_delivery.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ProductIngredientId implements java.io.Serializable {
    private static final long serialVersionUID = -1240064297013529836L;
    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "ingredient_id", nullable = false)
    private Integer ingredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProductIngredientId entity = (ProductIngredientId) o;
        return Objects.equals(this.ingredientId, entity.ingredientId) &&
                Objects.equals(this.productId, entity.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, productId);
    }

}