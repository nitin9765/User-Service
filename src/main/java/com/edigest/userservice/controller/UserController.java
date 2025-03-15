package com.edigest.userservice.controller;

import com.edigest.userservice.entity.User;
import com.edigest.userservice.models.UserDto;
import com.edigest.userservice.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/v1")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/createUpdate")
    public ResponseEntity<Object> createUpdate(@RequestHeader(value="x-user-id") @NonNull String userId, @RequestBody UserDto userDto){
        User user=userService.createOrUpdateUser(userId, userDto);
        return ResponseEntity.ok(
                UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                        .build()
        );
    }

    @GetMapping("/getUser")
    public ResponseEntity<Object> getUser(@RequestHeader(value="x-user-id") @NonNull String userId) {
        try{
            return ResponseEntity.ok(userService.getUser(userId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
