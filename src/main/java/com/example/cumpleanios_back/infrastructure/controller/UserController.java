package com.example.cumpleanios_back.infrastructure.controller;

import com.example.cumpleanios_back.application.services.EmailService;
import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.application.services.utils.EmailBody;
import com.example.cumpleanios_back.application.usecases.FindAllByBirthMonth;
import com.example.cumpleanios_back.application.usecases.FindEmployeesByBirthMonthUseCase;
import com.example.cumpleanios_back.domain.entities.Role;
import com.example.cumpleanios_back.domain.entities.RoleType;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import com.example.cumpleanios_back.domain.repositories.RoleRepository;
import com.example.cumpleanios_back.infrastructure.dto.auth.ErrorMessageResponse;
import com.example.cumpleanios_back.infrastructure.dto.user.UserBirthayDtoResponse;
import com.example.cumpleanios_back.infrastructure.dto.user.UserCreateDtoRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    private final EmailService emailService;

    private final RoleRepository roleRepository;

    private final FindAllByBirthMonth findAllByBirthMonth;

    @Autowired
    public UserController(FindEmployeesByBirthMonthUseCase findEmployeesByBirthMonthUseCase, FindAllByBirthMonth findAllByBirthMonth, UserService userService, EmailService emailService, RoleRepository roleRepository) {
        this.findAllByBirthMonth = findAllByBirthMonth;
        this.userService = userService;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/birthday")
    public ResponseEntity<?> getAllEmployeesByBirthMonth(@RequestParam("month") String month) {
        try {
            LocalDate currentMonth = LocalDate.parse(month + "-01");
            List<UserEntity> users = findAllByBirthMonth.execute(currentMonth);
            List<UserBirthayDtoResponse> dtoResponses = new ArrayList<>();
            for (UserEntity user : users) {
                dtoResponses.add(
                        UserBirthayDtoResponse.builder()
                                .dateBirth(user.getDateBirth())
                                .email(user.getEmail())
                                .name(user.getName())
                                .last_name(user.getLastName())
                                .age(this.userService.getAge(user))
                                .build()
                );
            }
            return ResponseEntity.ok(dtoResponses);
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<UserEntity> users = this.userService.indexUsers();
            List<UserEntity> employees = users.stream()
                    .filter(userEntity -> userEntity.getRoles().stream()
                            .anyMatch(role -> role.getRoleType() == RoleType.EMPLOYEE))
                    .toList();


            List<UserResponseDtoResponse> response = new ArrayList<>();

            for (UserEntity user : employees) {
                response.add(
                        UserResponseDtoResponse.builder()
                                .id(user.getId())
                                .name(user.getName())
                                .lastName(user.getLastName())
                                .email(user.getEmail())
                                .birthDate(user.getDateBirth())
                                .build()
                );
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {

            Optional<UserEntity> user = this.userService.findByID(id);

            if (user.isEmpty()) {
                var errorMessage = ErrorMessageResponse.builder()
                        .message("User not found")
                        .status(false)
                        .build();
                return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
            }


            if (user.get().getRoles().stream().anyMatch(role -> role.getRoleType() == RoleType.ADMIN)) {
                var errorMessage = ErrorMessageResponse.builder()
                        .message("You don't have enough permissions")
                        .status(false)
                        .build();
                return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
            }

            this.userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserCreateDtoRequest user) {
        try {
            Role employeeRole = Role.builder().roleType(RoleType.EMPLOYEE).build();
            employeeRole = roleRepository.save(employeeRole);

            var userEntityBuilder = UserEntity.builder().
                    name(user.name())
                    .email(user.email())
                    .dateBirth(user.birthDate())
                    .lastName(user.lastName())
                    .roles(Set.of(employeeRole))
                    .build();
            UserEntity created = this.userService.createUser(userEntityBuilder);

            var response = UserCreateDtoRequest.builder().
                    name(created.getName())
                    .lastName(created.getLastName())
                    .email(created.getEmail())
                    .birthDate(created.getDateBirth())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMail(@RequestBody EmailBody emailBody) {
        try {
            System.out.println("Iniciando envio de correo");
            var response = this.emailService.sendEmail(emailBody);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
