package com.nouhaila.spring_mvc.security.services;

import com.nouhaila.spring_mvc.security.entities.RolePatient;
import com.nouhaila.spring_mvc.security.entities.UserPatient;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPatient userPatient = accountService.loadUserByUsername(username);
        if (userPatient == null) throw new UsernameNotFoundException(String.format("User with username %s not found", username));
        String[] roles = userPatient.getRoles().stream().map(RolePatient::getRole).toArray(String[]::new);
        return User
                .withUsername(userPatient.getUsername())
                .password(userPatient.getPassword())
                .roles(roles).build();
    }
}
