package com.edigest.userservice.controller;

import com.edigest.userservice.entity.User;
import com.edigest.userservice.models.UserDto;
import com.edigest.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user/v1")
public class UserController {
    private final UserService userService;
    UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/createUpdate")
    public ResponseEntity<?> createUpdate(@RequestBody UserDto userDto){
        User user=userService.createOrUpdateUser(userDto);
        return ResponseEntity.ok(
                UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                        .build()
        );
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestBody UserDto userDto) {
        try{
            return ResponseEntity.ok(userService.getUser(userDto));
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
