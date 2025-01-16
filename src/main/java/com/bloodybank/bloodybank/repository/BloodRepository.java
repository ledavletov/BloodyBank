package com.bloodybank.bloodybank.repository;

import com.bloodybank.bloodybank.entity.Blood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BloodRepository extends JpaRepository<Blood, Integer> {
    Optional<Blood> findByName(String name);
}
