package com.example.springdatabasicdemo.repositories;

import com.example.springdatabasicdemo.models.Inspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InspectionRepository extends JpaRepository<Inspection, Long> {

    // Пример запроса для поиска проверок по статусу
    @Query("SELECT i FROM Inspection i WHERE i.status = :status")
    List<Inspection> findByStatus(String status);

    // Пример запроса для поиска проверок, проведенных после определённой даты
    @Query("SELECT i FROM Inspection i WHERE i.inspectionDate > :date")
    List<Inspection> findByInspectionDateAfter(LocalDate date);
}
