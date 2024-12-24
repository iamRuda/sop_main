package com.example.springdatabasicdemo.repositories;

import com.example.springdatabasicdemo.dtos.CargoDTO;
import com.example.springdatabasicdemo.models.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    // Пример запроса для поиска по весу, превышающему определённое значение
    @Query("SELECT c FROM Cargo c WHERE c.weight > :weight")
    List<Cargo> findByWeightGreaterThan(Double weight);
}
