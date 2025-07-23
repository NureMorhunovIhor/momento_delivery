package org.example.momento_delivery.controllers;

import lombok.RequiredArgsConstructor;
import org.example.momento_delivery.dtos.CreateAddressDTO;
import org.example.momento_delivery.services.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody CreateAddressDTO dto) {
        return ResponseEntity.ok(addressService.createAddress(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserAddresses(@PathVariable Integer userId) {
        return ResponseEntity.ok(addressService.getUserAddresses(userId));
    }
}

