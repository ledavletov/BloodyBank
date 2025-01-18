package com.bloodybank.bloodybank.service;


import com.bloodybank.bloodybank.dto.RegisterDto;
import com.bloodybank.bloodybank.entity.Blood;
import com.bloodybank.bloodybank.entity.User;
import com.bloodybank.bloodybank.exception.NoSuchBloodType;
import com.bloodybank.bloodybank.exception.UserNotFoundException;
import com.bloodybank.bloodybank.exception.WrongPasswordException;
import com.bloodybank.bloodybank.repository.BloodRepository;
import com.bloodybank.bloodybank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private BloodRepository bloodRepository;

    public void registerUser(RegisterDto dto) throws NoSuchBloodType {
        User user = new User();
        user.setName(dto.name());
        user.setSurname(dto.surname());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        Blood blood = getBloodType(dto.bloodType());
        user.setBlood_type(blood);
        userRepository.save(user);
    }

    private Blood getBloodType(String name) throws NoSuchBloodType {
        return bloodRepository.findByName(name).orElseThrow(() -> new NoSuchBloodType("There is no such blood type as " + name));
    }

    public void login(String email, String password) throws Exception {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            if(!byEmail.get().getPassword().equals(password)){
                throw new WrongPasswordException();
            }
        }
        throw new UserNotFoundException(email);
    }
}
