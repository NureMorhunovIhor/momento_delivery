package org.example.momento_delivery.services;


import lombok.RequiredArgsConstructor;
import org.example.momento_delivery.dtos.CreateAddressDTO;
import org.example.momento_delivery.entities.Address;
import org.example.momento_delivery.entities.User;
import org.example.momento_delivery.repositories.AddressRepository;
import org.example.momento_delivery.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepo;
    private final UserRepository userRepo;

    public Address createAddress(CreateAddressDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = new Address();
        address.setUser(user);
        address.setCity(dto.getCity());
        address.setStreet(dto.getStreet());
        address.setHouseNumber(dto.getHouseNumber());
        address.setApartment(dto.getApartment());
        address.setComment(dto.getComment());
        address.setIsDefault(dto.getIsDefault() != null && dto.getIsDefault());

        return addressRepo.save(address);
    }

    public List<Address> getUserAddresses(Integer userId) {
        User user = new User();
        user.setId(userId);
        return addressRepo.findByUser(user);
    }
}

