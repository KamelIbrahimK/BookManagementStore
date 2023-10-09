package com.example.onlinebookstoremanagementsystem.Dtos;

import com.example.onlinebookstoremanagementsystem.Enums.CategoryType;
import lombok.Data;

@Data
public class UpdateBookDto {
    private Integer id;
    private String name;
    private String authorName;
    private Boolean available;
    private CategoryType categoryType;

    public UpdateBookDto(Integer id, String name, String authorName, Boolean available, CategoryType categoryType) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.available = available;
        this.categoryType = categoryType;
    }
}
