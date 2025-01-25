package com.bloodybank.bloodybank.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
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
