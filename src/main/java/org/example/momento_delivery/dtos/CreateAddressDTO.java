package org.example.momento_delivery.dtos;

import lombok.Data;

@Data
public class CreateAddressDTO {
    private Integer userId;
    private String city;
    private String street;
    private String houseNumber;
    private String apartment;
    private String comment;
    private Boolean isDefault;
}
