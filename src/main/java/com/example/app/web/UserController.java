package com.example.app.web;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.app.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserResponse> users(@RequestParam String city) {
        return userService.findInCity(city).stream().map(UserResponse::of).toList();
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody CreateUserRequest request) {
        return UserResponse.of(userService.register(
                request.email(),
                request.birthDate() == null ? null : LocalDate.parse(request.birthDate()),
                request.street(),
                request.postalCode(),
                request.city()));
    }
}
