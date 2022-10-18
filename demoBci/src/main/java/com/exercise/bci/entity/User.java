package com.exercise.bci.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name= "users")
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    @OneToMany(
            mappedBy = "userPhone",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Phone> phones = new ArrayList<>();;
    private LocalDateTime created;
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;

    public User(String name, String email, String password, List<Phone> userPhones, String token) {
        this.userId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = true;
        this.phones = userPhones;
        this.token = token;
        this.created = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
    }
}
