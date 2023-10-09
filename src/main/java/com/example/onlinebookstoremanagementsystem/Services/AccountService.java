package com.example.onlinebookstoremanagementsystem.Services;

import com.example.onlinebookstoremanagementsystem.Dtos.AccountDto;
import com.example.onlinebookstoremanagementsystem.Dtos.LoginDto;
import com.example.onlinebookstoremanagementsystem.Dtos.LoginResponse;
import com.example.onlinebookstoremanagementsystem.Entities.Admin;
import com.example.onlinebookstoremanagementsystem.Entities.Customer;
import com.example.onlinebookstoremanagementsystem.Entities.User;
import com.example.onlinebookstoremanagementsystem.Enums.AccountType;
import com.example.onlinebookstoremanagementsystem.Repositories.AdminRepo;
import com.example.onlinebookstoremanagementsystem.Repositories.CustomerRepo;
import com.example.onlinebookstoremanagementsystem.Repositories.UserRepo;
import com.example.onlinebookstoremanagementsystem.Utils.Constants;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private CustomerRepo customerRepo;




    public boolean checkPasswordValidations(String password) {

        if (password.length() >= 10) {
            for (int i = 0; i < password.length(); i++) {
                if (Character.isUpperCase(password.charAt(i))) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    // 1. check username is not exist before
    // 2. check password is at least 10 letters and at least one of them is
    // capital
    // 3. save
    public String signUp(AccountDto accountDto) {
        User oldUser = userRepo.findByEmail(accountDto.getEmail());
        // isExist == false
        if (oldUser!=null) {
            return Constants.DUPLICATE_USER;
        }
        boolean isValidPassword = checkPasswordValidations(accountDto.getPassword());
        if (!isValidPassword) {
            return Constants.PASSWORD_VALIDATION_FAILURE;
        }
        if (accountDto.getAccountType().equals(AccountType.Admin)) {
            Admin admin = new Admin(accountDto.getEmail(), accountDto.getName(), accountDto.getAddress(),
                    accountDto.getPhoneNumber(), accountDto.getPassword());
            adminRepo.save(admin);
        } else if (accountDto.getAccountType().equals(AccountType.Customer)) {
            Customer customer = new Customer(accountDto.getEmail(), accountDto.getName(), accountDto.getAddress(),
                    accountDto.getPhoneNumber(), accountDto.getPassword());
            customerRepo.save(customer);
        }
        return Constants.SIGN_UP_SUCCESSFULLY;
    }

    public LoginResponse login(LoginDto loginDto) {
        LoginResponse loginResponse = new LoginResponse();
        User user = userRepo.findByEmail(loginDto.getEmail());
        if (user !=null)
        {
            boolean passCheckResponse = user.getPassword().equals(loginDto.getPassword());
            if ( passCheckResponse) {
                loginResponse.setMessage(Constants.LOGIN_SUCCESSFULLY);
                loginResponse.setUserId(user.getId());
                return loginResponse;
            } else {
                loginResponse.setMessage(Constants.WRONG_PASSWORD);
                return loginResponse;
            }
        } else {
            loginResponse.setMessage(Constants.WRONG_EMAIL);
            return loginResponse;
        }
    }
}
