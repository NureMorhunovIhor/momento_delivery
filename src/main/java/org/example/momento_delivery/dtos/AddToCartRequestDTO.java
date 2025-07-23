package org.example.momento_delivery.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AddToCartRequestDTO {
    private Integer userId;
    private Integer variantId;
    private Integer quantity;
    private List<Integer> ingredientIds;
}

