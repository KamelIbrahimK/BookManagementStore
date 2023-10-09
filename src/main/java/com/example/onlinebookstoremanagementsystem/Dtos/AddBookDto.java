package com.example.onlinebookstoremanagementsystem.Dtos;

import com.example.onlinebookstoremanagementsystem.Enums.CategoryType;
import lombok.Data;

@Data
public class AddBookDto {
    private String name;
    private String authorName;
    private CategoryType categoryType;

    public AddBookDto(String name, String authorName, CategoryType categoryType) {
        this.name = name;
        this.authorName = authorName;
        this.categoryType = categoryType;
    }
}
