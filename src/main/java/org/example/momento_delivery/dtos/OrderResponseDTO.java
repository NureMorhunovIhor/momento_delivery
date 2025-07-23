package org.example.momento_delivery.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResponseDTO {
    private Integer orderId;
    private String status;
    private BigDecimal totalPrice;
    private String paymentMethod;
    private String comment;
    private String city;
    private String street;
    private String houseNumber;
}
