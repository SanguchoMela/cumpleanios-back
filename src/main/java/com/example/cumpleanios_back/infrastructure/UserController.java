package com.example.cumpleanios_back.infrastructure;

import com.example.cumpleanios_back.application.services.UserService;
import com.example.cumpleanios_back.domain.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/hello")
    public String helloUnsafe() {
        return "Hello World";
    }

    @GetMapping("/hello-safe")
    public String helloSafe() {
        return "Hello world secured";
    }

    @GetMapping("/indexUsers")
    public List<UserEntity> indexUsers(){
        return this.userService.indexUsers();
    }
    @GetMapping("/showUsers/{id}")
    public UserEntity showUser(@PathVariable Long id){
        return this.userService.showUser(id);
    }
    @PostMapping("/createUsers")
    public UserEntity createUser(@RequestBody UserEntity users){
        return this.userService.createUser(users);
    }
    @PutMapping("/updateUsers/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody UserEntity users){
        return this.userService.updateUser(id, users);
    }
    @DeleteMapping("/deleteUsers/{id}")
    public String deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
        return "Empleado eliminado con exito";
    }

}
