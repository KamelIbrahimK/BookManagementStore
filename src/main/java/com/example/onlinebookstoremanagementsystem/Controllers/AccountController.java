package com.example.onlinebookstoremanagementsystem.Controllers;

import com.example.onlinebookstoremanagementsystem.Dtos.AccountDto;
import com.example.onlinebookstoremanagementsystem.Dtos.LoginDto;
import com.example.onlinebookstoremanagementsystem.Dtos.LoginResponse;
import com.example.onlinebookstoremanagementsystem.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/Account")
public class AccountController {
    @Autowired
    AccountService accountService;

    @PostMapping(path = "/signUp")
    public String signUp(@RequestBody AccountDto accountDto) {
        return accountService.signUp(accountDto);
    }

    @PostMapping(path = "/login")
    public LoginResponse login(@RequestBody LoginDto loginDto) {
        return accountService.login(loginDto);
    }

}
