package com.example.cumpleanios_back.infrastructure;

import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.application.usecases.FindAllByBirthMonth;
import com.example.cumpleanios_back.application.usecases.FindEmployeesByBirthMonthUseCase;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import com.example.cumpleanios_back.infrastructure.dto.user.UserBirthayDtoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    private final FindEmployeesByBirthMonthUseCase findEmployeesByBirthMonthUseCase;
    private final FindAllByBirthMonth findAllByBirthMonth;

    @Autowired
    public UserController(FindEmployeesByBirthMonthUseCase findEmployeesByBirthMonthUseCase, FindAllByBirthMonth findAllByBirthMonth) {
        this.findEmployeesByBirthMonthUseCase = findEmployeesByBirthMonthUseCase;
        this.findAllByBirthMonth = findAllByBirthMonth;
    }

    @GetMapping("/birthda")
    public ResponseEntity<?> getEmployeesByBirthMonth(@RequestParam("month") String month) {
        try {
            LocalDate currentMonth = LocalDate.parse(month + "-01");
            List<UserEntity> users = findEmployeesByBirthMonthUseCase.execute(currentMonth);
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

    @GetMapping("/indexUsers")
    public List<UserEntity> indexUsers() {
        return this.userService.indexUsers();
    }

    @GetMapping("/showUsers/{id}")
    public UserEntity showUser(@PathVariable Long id) {
        return this.userService.showUser(id);
    }

    @PostMapping("/createUsers")
    public UserEntity createUser(@RequestBody UserEntity users) {
        return this.userService.createUser(users);
    }

    @PutMapping("/updateUsers/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity users) {
        return this.userService.updateUser(id, users);
    }

    @DeleteMapping("/deleteUsers/{id}")
    public String deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
        return "Empleado eliminado con exito";
    }

}
