package com.example.cumpleanios_back.config;

import com.example.cumpleanios_back.application.services.UserDetailServiceImpl;
import com.example.cumpleanios_back.config.filters.JwtTokenValidator;
import com.example.cumpleanios_back.config.jwt.JwtUtils;
import com.example.cumpleanios_back.domain.entities.Role;
import com.example.cumpleanios_back.domain.entities.RoleType;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import com.example.cumpleanios_back.domain.repositories.RoleRepository;
import com.example.cumpleanios_back.domain.repositories.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConf {
    @Autowired
    @Lazy
    UserDetailServiceImpl userDetailService;

    @Autowired
    JwtUtils jwtUtils;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CommandLineRunner createDefaultUser(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {

                Role adminRole = roleRepository.findByRoleType(RoleType.ADMIN).orElse(null);

                if (adminRole == null) {
                    adminRole = Role.builder().roleType(RoleType.ADMIN).build();
                    adminRole = roleRepository.save(adminRole);
                }

                UserEntity defaultUserEntity = UserEntity.builder().name("Admin").lastName("Admin").username("admin").email("admin@example.com").password(passwordEncoder.encode("admin123")).roles(Set.of(adminRole)).build();

                userRepository.save(defaultUserEntity);
            }
        };
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationProvider authenticationProvider) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(http -> {
            // EndPoints publicos
            http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();

            // EndPoints Privados
            http.requestMatchers("/users/**").authenticated();
//                    http.requestMatchers(HttpMethod.POST, "/method/post").authenticated();
//                    http.requestMatchers(HttpMethod.DELETE, "/method/delete").authenticated();
//                    http.requestMatchers(HttpMethod.PUT, "/method/put").authenticated();

            http.anyRequest().denyAll();
        }).addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class).build();
    }

    @Bean
    public AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (request, response, authException) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"); // 401
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class).userDetailsService(userDetailService).passwordEncoder(passwordEncoder).and().build();
    }

}
