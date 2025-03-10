package com.edigest.userservice.service;

import com.edigest.userservice.entity.User;
import com.edigest.userservice.models.UserDto;
import com.edigest.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
public class UserService {
    private UserRepository userRepository;
    UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User createOrUpdateUser(UserDto userDto){
        UnaryOperator<User> updateUser= user->{
            if(userDto.getName()!=null && !userDto.getName().isEmpty()) user.setName(userDto.getName());
            if (userDto.getEmail() != null && !userDto.getEmail().isEmpty()) user.setEmail(userDto.getEmail());
            if(userDto.getPhoneNumber()!=null && !userDto.getPhoneNumber().isEmpty()) user.setPhoneNumber(userDto.getPhoneNumber());
            return userRepository.save(user);
        };
        Supplier<User> createUser=()-> {
            // TODO
            User newUser=User.builder()
                    .userId(userDto.getUserId())
                    .id(Instant.now().toEpochMilli())
                    .phoneNumber(userDto.getPhoneNumber())
                    .name(userDto.getName())
                    .email(userDto.getEmail()).build();
            return userRepository.save(newUser);
        };
        return userRepository.findByUserId(userDto.getUserId())
                .map(updateUser).orElseGet(createUser);
    }

    public UserDto getUser(UserDto userDto) throws Exception{
        return userRepository.findByUserId(userDto.getUserId()).map(value->
                UserDto.builder()
                        .name(value.getName())
                        .phoneNumber(value.getPhoneNumber())
                        .email(value.getEmail())
                        .build()
        ).orElseThrow(()->new Exception("User not found"));
    }
}
