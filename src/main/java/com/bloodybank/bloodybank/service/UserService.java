package com.bloodybank.bloodybank.service;


import com.bloodybank.bloodybank.dto.RegisterDto;
import com.bloodybank.bloodybank.entity.Blood;
import com.bloodybank.bloodybank.entity.Transaction;
import com.bloodybank.bloodybank.entity.User;
import com.bloodybank.bloodybank.exception.NoSuchBloodType;
import com.bloodybank.bloodybank.exception.NoTransactionException;
import com.bloodybank.bloodybank.exception.UserNotFoundException;
import com.bloodybank.bloodybank.exception.WrongPasswordException;
import com.bloodybank.bloodybank.repository.BloodRepository;
import com.bloodybank.bloodybank.repository.TransactionRepository;
import com.bloodybank.bloodybank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final TransactionRepository transactionRepository;
    private UserRepository userRepository;
    private BloodRepository bloodRepository;

    public void registerUser(RegisterDto dto) throws NoSuchBloodType {
        User user = new User();
        user.setName(dto.name());
        user.setSurname(dto.surname());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setCount(dto.age());

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

    public String getUser(String email) throws UserNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            return byEmail.get().toString();
        }
        throw new UserNotFoundException(email);
    }

    public int incrementBloodCount(String email) throws UserNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            User user = byEmail.get();
            int count = user.getCount();
            user.setCount(++count);
            userRepository.save(user);
            return count;
        }
        throw new UserNotFoundException(email);
    }

    public boolean canDonate(String email) throws UserNotFoundException{
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            User q = byEmail.get();
            return isValidAge(q.getAge());
        }
        throw new UserNotFoundException(email);
    }

    private boolean isValidAge(int age){
       if(age >= 18 && age <= 65){
           return true;
       }
       return false;
    }

    public int countDonation(String email) throws UserNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            User user = byEmail.get();
            return user.getCount();
        }
        throw new UserNotFoundException(email);
    }

    public List<String> getBlood(String email) throws UserNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if (byEmail.isPresent()){
            User user = byEmail.get();
            return transactionRepository.findAllByBloodType(user.getBlood_type().getName()).stream().map(TransactionConverter::convert).toList();
        } else{
            throw new UserNotFoundException(email);
        }
    }
    public void extractBlood(String id, int userId) {
        int bloodId = Integer.parseInt(id);
        Optional<Transaction> t = transactionRepository.findById(bloodId);
        if(t.isPresent()){
            Transaction transaction = t.get();
            transaction.setReceiverId(userId);
            transactionRepository.save(transaction);
        }
        throw new NoTransactionException(String.valueOf(id));
    }

}
