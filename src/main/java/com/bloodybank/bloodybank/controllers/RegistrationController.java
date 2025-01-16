package com.bloodybank.bloodybank.controllers;

import com.bloodybank.bloodybank.dto.LoginDto;
import com.bloodybank.bloodybank.dto.RegisterDto;
import com.bloodybank.bloodybank.exception.NoSuchBloodType;
import com.bloodybank.bloodybank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private UserService userService;

    @PostMapping ("/register")
    public void register(@RequestBody RegisterDto dto){
        try {
            userService.registerUser(dto);
            System.out.println("Registered a new user");
        } catch (NoSuchBloodType e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginDto dto){

    }




}
