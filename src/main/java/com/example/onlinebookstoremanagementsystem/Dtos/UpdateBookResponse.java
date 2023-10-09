package com.example.onlinebookstoremanagementsystem.Dtos;

import com.example.onlinebookstoremanagementsystem.Entities.Book;
import lombok.Data;

@Data
public class UpdateBookResponse {
    private String Message;
    private Book book;
}
