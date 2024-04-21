package com.nouhaila.spring_mvc.security.services;

import com.nouhaila.spring_mvc.security.entities.RolePatient;
import com.nouhaila.spring_mvc.security.entities.UserPatient;

public interface AccountService {
    UserPatient saveUser(String username, String password, String email, String confirmPassword);
    RolePatient saveRole(String role);
    void addRoleToUser(String username, String roleName);
    void removeRoleToUser(String username, String roleName);
    UserPatient loadUserByUsername(String username);


}
