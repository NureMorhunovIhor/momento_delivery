package org.example.momento_delivery.controllers;

import lombok.RequiredArgsConstructor;
import org.example.momento_delivery.entities.DeliveryZone;
import org.example.momento_delivery.repositories.DeliveryZoneRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-zones")
@RequiredArgsConstructor
public class DeliveryZoneController {

    private final DeliveryZoneRepository zoneRepo;

    @GetMapping
    public List<DeliveryZone> getAllZones() {
        return zoneRepo.findAll();
    }

    @PostMapping
    public DeliveryZone createZone(@RequestBody DeliveryZone zone) {
        return zoneRepo.save(zone);
    }

    @PutMapping("/{id}")
    public DeliveryZone updateZone(@PathVariable Integer id, @RequestBody DeliveryZone updated) {
        DeliveryZone zone = zoneRepo.findById(id).orElseThrow();
        zone.setLocationName(updated.getLocationName());
        zone.setDeliveryPrice(updated.getDeliveryPrice());
        zone.setFreeDeliveryThreshold(updated.getFreeDeliveryThreshold());
        return zoneRepo.save(zone);
    }

    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable Integer id) {
        zoneRepo.deleteById(id);
    }
}

