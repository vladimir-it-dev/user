package org.example.app.controller;

import lombok.RequiredArgsConstructor;
import org.example.app.dto.UserRequest;
import org.example.app.dto.UserResponse;
import org.example.app.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public String createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @GetMapping("/{id}")
    public UserResponse getInfoUser(@PathVariable("id") Long idUser) {
        return userService.getInfoUser(idUser);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable("id") Long idUser, @RequestBody UserRequest userRequest) {
        return userService.updateUser(idUser, userRequest);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long idUser) {
        return userService.deleteUser(idUser);
    }
}
