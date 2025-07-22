package org.example.momento_delivery.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    public String email;
    public String phone;
    public String password;
    public String name;
}

