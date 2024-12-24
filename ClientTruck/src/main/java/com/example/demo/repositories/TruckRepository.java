package com.example.demo.repositories;

import com.example.demo.models.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TruckRepository extends JpaRepository<Truck, Long> {

    // Метод для получения первого грузовика по ID (первый по порядку)
    Optional<Truck> findFirstByOrderByIdAsc();
}
