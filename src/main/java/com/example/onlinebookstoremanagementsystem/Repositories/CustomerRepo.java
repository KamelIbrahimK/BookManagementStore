package com.example.onlinebookstoremanagementsystem.Repositories;

import com.example.onlinebookstoremanagementsystem.Entities.Customer;
import com.example.onlinebookstoremanagementsystem.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,Integer> {
    Customer findByCustomerUserId(Integer Id);
}
