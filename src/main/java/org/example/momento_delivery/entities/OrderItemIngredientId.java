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
public class OrderItemIngredientId implements java.io.Serializable {
    private static final long serialVersionUID = -1349152609499743337L;
    @Column(name = "order_item_id", nullable = false)
    private Integer orderItemId;

    @Column(name = "ingredient_id", nullable = false)
    private Integer ingredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderItemIngredientId entity = (OrderItemIngredientId) o;
        return Objects.equals(this.ingredientId, entity.ingredientId) &&
                Objects.equals(this.orderItemId, entity.orderItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, orderItemId);
    }

}