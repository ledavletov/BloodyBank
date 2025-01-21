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

    @GetMapping("/canDonate/{email}")
    public ResponseEntity<String> canDonate(@PathVariable String email){
        try {
            if(userService.canDonate(email)){
                return ResponseEntity.ok("Age is valid");
            }
            return ResponseEntity.badRequest().body("Age is not valid");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/amountDonations/{email}")
    public ResponseEntity<String> countDonation(@PathVariable String email){
        try {
            if(userService.canDonate(email)){
                int i = userService.countDonation(email);
                return ResponseEntity.ok(String.valueOf(i));
            }
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.badRequest().body("User not found");
    }

}
