package com.example.onlinebookstoremanagementsystem.services;

import com.example.onlinebookstoremanagementsystem.Dtos.AccountDto;
import com.example.onlinebookstoremanagementsystem.Dtos.LoginDto;
import com.example.onlinebookstoremanagementsystem.Dtos.LoginResponse;
import com.example.onlinebookstoremanagementsystem.Entities.User;
import com.example.onlinebookstoremanagementsystem.Enums.AccountType;
import com.example.onlinebookstoremanagementsystem.Repositories.AdminRepo;
import com.example.onlinebookstoremanagementsystem.Repositories.CustomerRepo;
import com.example.onlinebookstoremanagementsystem.Repositories.UserRepo;
import com.example.onlinebookstoremanagementsystem.Services.AccountService;
import com.example.onlinebookstoremanagementsystem.Utils.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    private UserRepo userRepo;
    @Mock
    private AdminRepo adminRepo;
    @Mock
    private CustomerRepo customerRepo;
    @InjectMocks
    private AccountService accountService;

    @Test
    public void checkPasswordValidations_wrongLength() {
        assertFalse(accountService.checkPasswordValidations("test"));
    }

    @Test
    public void checkPasswordValidations_wrongFormat() {
        assertFalse(accountService.checkPasswordValidations("testpassword"));
    }

    @Test
    public void checkPasswordValidations_success() {
        assertTrue(accountService.checkPasswordValidations("testPassword"));
    }

    @Test
    public void signUp_oldUser_fail() {
        when(userRepo.findByEmail(any())).thenReturn(new User());
        assertEquals(Constants.DUPLICATE_USER, accountService.signUp(new AccountDto()));
    }

    @Test
    public void signUp_invalidPassword_fail() {
        when(userRepo.findByEmail(any())).thenReturn(null);
        AccountDto dto = new AccountDto();
        dto.setPassword("test");

        assertEquals(Constants.PASSWORD_VALIDATION_FAILURE, accountService.signUp(dto));
    }

    @Test
    public void signUp_admin_success() {
        when(userRepo.findByEmail(any())).thenReturn(null);
        String response=accountService.signUp(getDummyAccountDto(true));
        verify(adminRepo).save(any());
        assertEquals(Constants.SIGN_UP_SUCCESSFULLY , response);
    }
    @Test
    public void signUp_customer_success() {
        when(userRepo.findByEmail(any())).thenReturn(null);
        String response=accountService.signUp(getDummyAccountDto(false));
        verify(customerRepo).save(any());
        assertEquals(Constants.SIGN_UP_SUCCESSFULLY , response);

    }

    private AccountDto getDummyAccountDto(boolean isAdmin) {
        AccountDto dto = new AccountDto();
        dto.setPassword("testPassword");
        if(isAdmin)
             dto.setAccountType(AccountType.Admin);
        else
            dto.setAccountType(AccountType.Customer);
        dto.setName("testNAme");
        dto.setEmail("test@gmail.com");
        dto.setAddress("address");
        dto.setPhoneNumber("01122136137");
        return dto;
    }
    @Test
    public void login_wrongEmail() {
        when(userRepo.findByEmail(any())).thenReturn(null);
        LoginResponse response=accountService.login(new LoginDto());
        assertEquals(Constants.WRONG_EMAIL , response.getMessage());
    }
    @Test
    public void login_wrongPassword() {
        User user =new User();
        user.setPassword("123");
        when(userRepo.findByEmail(any())).thenReturn(user);
        LoginDto dto=new LoginDto();
        dto.setPassword("234");
        LoginResponse response=accountService.login(dto);
        assertEquals(Constants.WRONG_PASSWORD , response.getMessage());
    }
    @Test
    public void login_success() {
        when(userRepo.findByEmail(any())).thenReturn(new User());
        User user =new User();
        user.setPassword("123");
        user.setId(1);
        when(userRepo.findByEmail(any())).thenReturn(user);
        LoginDto dto=new LoginDto();
        dto.setPassword("123");
        LoginResponse response=accountService.login(dto);
        assertEquals(Constants.LOGIN_SUCCESSFULLY , response.getMessage());
        assertEquals(1l , response.getUserId().longValue());

    }


}

