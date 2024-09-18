package com.example.cumpleanios_back.infrastructure;

import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.application.usecases.FindEmployeesByBirthMonthUseCase;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import com.example.cumpleanios_back.infrastructure.dto.ErrorMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    private final FindEmployeesByBirthMonthUseCase findEmployeesByBirthMonthUseCase;

    @Autowired
    public UserController(FindEmployeesByBirthMonthUseCase findEmployeesByBirthMonthUseCase) {
        this.findEmployeesByBirthMonthUseCase = findEmployeesByBirthMonthUseCase;
    }

    @GetMapping("/birthday")
    public ResponseEntity<?> getEmployeesByBirthMonth(@RequestParam("month") String month) {
        try {
            LocalDate currentMonth = LocalDate.parse(month + "-01");
            List<UserEntity> users = findEmployeesByBirthMonthUseCase.execute(currentMonth);
            return ResponseEntity.ok(users);
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
