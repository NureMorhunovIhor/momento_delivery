package org.example.momento_delivery.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartItemDTO {
    private Integer id;
    private String productName;
    private String variantLabel;
    private BigDecimal price;
    private Integer quantity;
    private List<String> ingredientNames;
}
