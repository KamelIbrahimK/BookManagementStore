package com.example.onlinebookstoremanagementsystem.Repositories;

import com.example.onlinebookstoremanagementsystem.Entities.Book;
import com.example.onlinebookstoremanagementsystem.Enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepo extends JpaRepository<Book,Integer> {
    Book findByName(String name);
    List<Book>findByCategoryType(CategoryType categoryType);
    List<Book> findByIdIn(Set<Integer> ids);
}
