package com.taskmanager.task.controller;

import com.taskmanager.task.TaskDTO.UserRequest;
import com.taskmanager.task.TaskDTO.UserResponse;
import com.taskmanager.task.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "UserController",description = "RestApi for User Controller")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserResponse>createUser(@RequestBody UserRequest userRequest)
    {
        UserResponse user=userService.createUser(userRequest);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers()
    {
        List<UserResponse>allUsers=userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id)
    {
        UserResponse user=userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse>updateUserById(@PathVariable Long id,@RequestBody UserRequest userRequest)
    {
        UserResponse user=userService.updateUserById(id,userRequest);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id)
    {
        String res=userService.deleteUserById(id);
        return ResponseEntity.ok(res);
    }

}
