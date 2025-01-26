package com.bloodybank.bloodybank.controllers;

import com.bloodybank.bloodybank.dto.SimpleDto;
import com.bloodybank.bloodybank.exception.NoTransactionException;
import com.bloodybank.bloodybank.exception.UserNotFoundException;
import com.bloodybank.bloodybank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            int i = userService.donate(email);
            return ResponseEntity.ok(String.valueOf(i));
        } catch (Exception e) {
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
            return ResponseEntity.badRequest().body("Your age is not valid");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/getBlood/{email}")
    public ResponseEntity getBlood(@PathVariable String email){
        try{
            return ResponseEntity.ok(userService.getBlood(email));
        }catch(UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/extract/{id}/{userId}")
    public ResponseEntity<String> extract(@PathVariable int userId, @PathVariable String id){
        try {
            userService.extractBlood(id, userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Extracted");
    }

    @GetMapping("/allBloods")
    public ResponseEntity<List<String>> allBloods(){
        return ResponseEntity.ok(userService.extractAllBlood());
    }

}
