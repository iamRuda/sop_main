package com.example.springdatabasicdemo.repositories;

import com.example.springdatabasicdemo.models.InspectedPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspectedPersonRepository extends JpaRepository<InspectedPerson, Long> {

    // Пример запроса для поиска по номеру паспорта
    @Query("SELECT p FROM InspectedPerson p WHERE p.passportNumber = :passportNumber")
    List<InspectedPerson> findByPassportNumber(String passportNumber);
}
