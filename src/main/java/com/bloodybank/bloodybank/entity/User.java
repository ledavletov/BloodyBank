package com.bloodybank.bloodybank.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String surname;
    private String email;
    private String password;
    private int age;

    @ManyToOne
    @JoinColumn(name = "blood_id")
    private Blood blood_type;

    private int count;

    @OneToMany(mappedBy = "receiverId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Transaction> transactions;
}
