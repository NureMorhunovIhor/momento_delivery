package org.example.momento_delivery.repositories;

import org.example.momento_delivery.entities.Address;
import org.example.momento_delivery.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUser(User user);
}
