package com.example.onlinebookstoremanagementsystem.Repositories;

import com.example.onlinebookstoremanagementsystem.Entities.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepo extends JpaRepository<History,Integer> {
    List<History> findByCustomerId(Integer customerId);
}
