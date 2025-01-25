package com.bloodybank.bloodybank.service;


import com.bloodybank.bloodybank.dto.RegisterDto;
import com.bloodybank.bloodybank.dto.converter.TransactionConverter;
import com.bloodybank.bloodybank.dto.converter.UserConverter;
import com.bloodybank.bloodybank.entity.Blood;
import com.bloodybank.bloodybank.entity.Transaction;
import com.bloodybank.bloodybank.entity.User;
import com.bloodybank.bloodybank.exception.*;
import com.bloodybank.bloodybank.repository.BloodRepository;
import com.bloodybank.bloodybank.repository.TransactionRepository;
import com.bloodybank.bloodybank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private BloodRepository bloodRepository;
    private TransactionRepository transactionRepository;

    public void registerUser(RegisterDto dto) throws NoSuchBloodType {
        User user = new User();
        user.setName(dto.name());
        user.setSurname(dto.surname());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setAge(dto.age());

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
        else
            throw new UserNotFoundException(email);
    }

    public String getUser(String email) throws UserNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            return UserConverter.convert(byEmail.get());
        }
        throw new UserNotFoundException(email);
    }

    public int donate(String email) throws UserNotFoundException {
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            User user = byEmail.get();
            Transaction t = new Transaction();
            t.setBloodType(user.getBlood_type());
            t.setSender(user);
            transactionRepository.save(t);
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
       return age >= 18 && age <= 65;
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
            Blood blood = user.getBlood_type();
            return transactionRepository.findAllByBloodType(getBloodList(blood)).stream().map(TransactionConverter::convert).toList();
        } else{
            throw new UserNotFoundException(email);
        }
    }

    public void extractBlood(String id, int userId) throws Exception {
        int bloodId = Integer.parseInt(id);
        Optional<Transaction> t = transactionRepository.findById(bloodId);
        if(t.isPresent()){
            Transaction transaction = t.get();
            if (transaction.getReceiverId() != 0)
                throw new TransactionException("There is already a receiver with this id");
            transaction.setReceiverId(userId);
            transactionRepository.save(transaction);
        }
        else
            throw new NoTransactionException(String.valueOf(id));
    }

    private List<Integer> getBloodList(Blood type){
        int bloodId = type.getId();
        List<Integer> bloods = new ArrayList<>();
        switch (bloodId){
            case 1: 
                bloods.add(1); 
                bloods.add(3);
                break;
            case 2:
                bloods.add(2);
                bloods.add(3);
                break;
            case 3:
                bloods.add(3);
                break; 
            case 4:
                bloods.add(4);
                bloods.add(3);
                bloods.add(2);
                bloods.add(1);
                break;
        }
        return bloods;
    }

}
