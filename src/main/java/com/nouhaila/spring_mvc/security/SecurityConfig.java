package com.nouhaila.spring_mvc.security;

import com.nouhaila.spring_mvc.security.services.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {
    private PasswordEncoder passwordEncoder;
    private UserDetailsServiceImpl userDetailsService;


    //@Bean
    public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    //@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("nouhaila")
                        .password(passwordEncoder.encode("1234"))
                        .roles("USER")
                        .build(),
                User.withUsername("admin")
                        .password(passwordEncoder.encode("1234"))
                        .roles("ADMIN", "USER"  )
                        .build(),
                User.withUsername("leila")
                        .password(passwordEncoder.encode("1234"))
                        .roles("USER")
                        .build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(
                (form) -> form.defaultSuccessUrl("/", true).
                        loginPage("/login").
                        permitAll()

        );
        http.rememberMe(
                (rememberMe) -> rememberMe.key("remember-me").
                        rememberMeServices(new TokenBasedRememberMeServices("remember-me", inMemoryUserDetailsManager()))

        );
        http.authorizeHttpRequests(
                (auth) -> auth
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/webjars/**").permitAll()
        );
        http.authorizeHttpRequests((auth) -> auth.anyRequest().authenticated()

        );
        http.exceptionHandling(
                (exception) -> exception.accessDeniedPage("/notAuthorized")
        );
        http.userDetailsService(userDetailsService);
        return http.build();

    }
}
