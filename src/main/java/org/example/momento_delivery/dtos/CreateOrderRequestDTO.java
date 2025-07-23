package org.example.momento_delivery.dtos;

import lombok.Data;

@Data
public class CreateOrderRequestDTO {
    private Integer userId;
    private Integer addressId;
    private String paymentMethod;
    private String comment;
}

