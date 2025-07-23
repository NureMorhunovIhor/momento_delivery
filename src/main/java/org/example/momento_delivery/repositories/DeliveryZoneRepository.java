package org.example.momento_delivery.repositories;

import org.example.momento_delivery.entities.DeliveryZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryZoneRepository extends JpaRepository<DeliveryZone, Integer> {
    Optional<DeliveryZone> findByLocationName(String locationName);
}
