package org.example.auth.controller;

import jakarta.validation.Valid;
import org.example.auth.dto.UserDto;
import org.example.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> userDto(
            @Valid @RequestBody UserDto userDto
    ){
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('user','admin')")
    public ResponseEntity<UserDto> getMyUserInfo(){
        return ResponseEntity.ok(userService.getMyUserWithAuthorities());
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UserDto> getUserInfo(
            @PathVariable String username
    ){
        return ResponseEntity.ok(userService.getUserWithAuthorities(username));
    }
}
