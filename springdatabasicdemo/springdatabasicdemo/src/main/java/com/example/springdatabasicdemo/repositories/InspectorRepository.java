package com.example.springdatabasicdemo.repositories;

import com.example.springdatabasicdemo.models.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {

    // Пример запроса для поиска по табельному номеру
    @Query("SELECT i FROM Inspector i WHERE i.employeeNumber = :employeeNumber")
    List<Inspector> findByEmployeeNumber(String employeeNumber);
}
