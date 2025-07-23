package org.example.momento_delivery.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDTO {
    private Integer id;
    private String status;
    private BigDecimal totalPrice;
    private String paymentMethod;
    private String comment;
    private String userName; // або userId
}

