package com.bloodybank.bloodybank.controllers;

import com.bloodybank.bloodybank.exception.UserNotFoundException;
import com.bloodybank.bloodybank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {

    private UserService userService;


    @GetMapping("/profile/{email}")
    public ResponseEntity<String> profile(@PathVariable String email){
        try {
            return ResponseEntity.ok(userService.getUser(email));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/donate/{email}")
    public ResponseEntity<String> donate(@PathVariable String email){
        try {
            int i = userService.incrementBloodCount(email);
            return ResponseEntity.ok(String.valueOf(i));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
