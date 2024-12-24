package com.example.springdatabasicdemo.repositories;

import com.example.springdatabasicdemo.models.Truck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {

    // Пример запроса для поиска грузовиков по году выпуска
    @Query("SELECT t FROM Truck t WHERE t.yearOfManufacture = :year")
    List<Truck> findByYearOfManufacture(Integer year);
}
