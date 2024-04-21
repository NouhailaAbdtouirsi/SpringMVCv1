package com.nouhaila.spring_mvc.security.repositories;

import com.nouhaila.spring_mvc.security.entities.UserPatient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPatientRepo extends JpaRepository<UserPatient, String> {
    UserPatient findByUsername(String username);
}
