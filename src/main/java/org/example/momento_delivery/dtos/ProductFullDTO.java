package org.example.momento_delivery.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductFullDTO {
    private Integer id;
    private String name;
    private String description;
    private BigDecimal basePrice;
    private String imageUrl;
    private String categoryName;
    private List<String> variants;
    private List<String> ingredients;
}

