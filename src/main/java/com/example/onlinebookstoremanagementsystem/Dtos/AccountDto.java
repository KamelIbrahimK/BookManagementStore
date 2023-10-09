package com.example.onlinebookstoremanagementsystem.Dtos;

import com.example.onlinebookstoremanagementsystem.Enums.AccountType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountDto {
    private String email;
    private String name;
    private String address;
    private String phoneNumber;
    private String password;
    private AccountType accountType;

    public AccountDto(String email, String name, String address, String phoneNumber, String password, AccountType accountType) {
        this.email = email;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.accountType = accountType;
    }
}
