package com.example.cumpleanios_back.infrastructure.controller;

import com.example.cumpleanios_back.application.services.EmailService;
import com.example.cumpleanios_back.application.services.RoleService;
import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.application.services.utils.EmailBody;
import com.example.cumpleanios_back.application.usecases.FindAllByBirthMonth;
import com.example.cumpleanios_back.application.usecases.FindEmployeesByBirthMonthUseCase;
import com.example.cumpleanios_back.domain.entities.Role;
import com.example.cumpleanios_back.domain.entities.RoleType;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import com.example.cumpleanios_back.infrastructure.dto.user.UserResponseDtoResponse;
import com.example.cumpleanios_back.infrastructure.dto.auth.ErrorMessageResponse;
import com.example.cumpleanios_back.infrastructure.dto.user.UserBirtdhayDtoResponse;
import com.example.cumpleanios_back.infrastructure.dto.user.UserCreateDtoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    private final EmailService emailService;


    private final FindAllByBirthMonth findAllByBirthMonth;

    private final RoleService roleService;

    @Autowired
    public UserController(FindEmployeesByBirthMonthUseCase findEmployeesByBirthMonthUseCase, FindAllByBirthMonth findAllByBirthMonth, UserService userService, EmailService emailService, RoleService roleService) {
        this.findAllByBirthMonth = findAllByBirthMonth;
        this.userService = userService;
        this.emailService = emailService;
        this.roleService = roleService;
    }

    @GetMapping("/birthday")
    public ResponseEntity<?> getAllEmployeesByBirthMonth(@RequestParam("month") String month) {
        try {
            LocalDate currentMonth = LocalDate.parse(month + "-01");
            List<UserEntity> users = findAllByBirthMonth.execute(currentMonth);
            List<UserBirtdhayDtoResponse> dtoResponses = new ArrayList<>();
            for (UserEntity user : users) {
                dtoResponses.add(
                        UserBirtdhayDtoResponse.builder()
                                .dateBirth(user.getDateBirth())
                                .email(user.getEmail())
                                .name(user.getName())
                                .last_name(user.getLastName())
                                .phone(user.getPhone())
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
                                .phone(user.getPhone())
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
            Role employeeRole = roleService.getOrCreate(RoleType.EMPLOYEE);

            var userEntityBuilder = UserEntity.builder().
                    name(user.name())
                    .email(user.email())
                    .dateBirth(user.birthDate())
                    .lastName(user.lastName())
                    .roles(Set.of(employeeRole))
                    .phone(user.phone())
                    .build();
            UserEntity created = this.userService.createUser(userEntityBuilder);

            var response = UserCreateDtoRequest.builder().
                    name(created.getName())
                    .lastName(created.getLastName())
                    .email(created.getEmail())
                    .birthDate(created.getDateBirth())
                    .phone(created.getPhone())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            var errorMessage = ErrorMessageResponse.builder()
                    .message(e.getMessage())
                    .status(false)
                    .build();
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

    }

}
