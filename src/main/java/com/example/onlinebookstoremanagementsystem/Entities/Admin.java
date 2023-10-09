package com.example.onlinebookstoremanagementsystem.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "admin_user_id")
    private User adminUser;


    public Admin(String email, String name, String address, String phoneNumber, String password) {
        User user=new User( name,  address,  phoneNumber,  email,  password);
        this.adminUser=user;
    }
}

