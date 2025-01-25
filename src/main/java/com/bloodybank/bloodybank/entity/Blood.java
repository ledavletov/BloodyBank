package com.bloodybank.bloodybank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "blood")
public class Blood {

    @Id
    private int id;

    private String name;

    @OneToMany(mappedBy = "blood_type", cascade = jakarta.persistence.CascadeType.ALL)
    private List<User> users;

    @OneToMany(mappedBy = "bloodType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> transactions;
}
