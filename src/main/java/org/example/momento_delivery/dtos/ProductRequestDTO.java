package org.example.momento_delivery.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private BigDecimal basePrice;
    private String imageUrl;
    private Integer categoryId;
    private List<String> variants;
    private List<Integer> ingredientIds;
}

