package com.nouhaila.spring_mvc.repositories;

import com.nouhaila.spring_mvc.entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepo extends JpaRepository<Patient, Long> {
    Page<Patient> findByNameContains(String keyword, Pageable pageable);
    @Query("select p from Patient p where p.name like :x")
    Page<Patient> chercher(String x, Pageable pageable);
}
