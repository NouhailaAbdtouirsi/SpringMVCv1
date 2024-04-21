package com.nouhaila.spring_mvc.security.repositories;

import com.nouhaila.spring_mvc.security.entities.RolePatient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePatientRepo extends JpaRepository<RolePatient, String> {
}
