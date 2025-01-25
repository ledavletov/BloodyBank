package com.bloodybank.bloodybank.repository;

import com.bloodybank.bloodybank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Optional<Transaction> findBySenderId(int id);

    @Query("SELECT t FROM Transaction t WHERE t.bloodType.name = :bloodType")
    List<Transaction> findByBloodType(@Param("bloodType") String bloodType);

    Optional<Transaction> findById(int id);
}
