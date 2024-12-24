package com.example.demo.controllers;

import com.example.demo.models.Truck;
import com.example.demo.repositories.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trucks")
public class TruckRestController {

    private final TruckRepository truckRepository;

    @Autowired
    public TruckRestController(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    // Получение списка всех грузовиков
    @GetMapping
    public ResponseEntity<List<Truck>> getAllTrucks() {
        List<Truck> trucks = truckRepository.findAll();
        if (trucks.isEmpty()) {
            return ResponseEntity.notFound().build();  // Если список пустой, возвращаем 404
        }
        return ResponseEntity.ok(trucks);  // Если грузовики найдены, возвращаем 200 и список
    }

    // Получение грузовика по ID (для примера)
    @GetMapping("/{id}")
    public ResponseEntity<Truck> getTruckById(@PathVariable Long id) {
        return truckRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
