package com.example.onlinebookstoremanagementsystem.Entities;

import com.example.onlinebookstoremanagementsystem.Enums.CategoryType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
@NoArgsConstructor
@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String authorName;
    private Boolean available=true;
    private CategoryType categoryType;
    private LocalDateTime borrowingDate;
    private LocalDateTime returnDate;

    public Book(String name, String authorName,  CategoryType categoryType) {
        this.name = name;
        this.authorName = authorName;
        this.categoryType = categoryType;
    }
}
