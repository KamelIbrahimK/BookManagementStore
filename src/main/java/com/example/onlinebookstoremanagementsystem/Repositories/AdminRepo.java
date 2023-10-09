package com.example.onlinebookstoremanagementsystem.Repositories;

import com.example.onlinebookstoremanagementsystem.Entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer> {
}
