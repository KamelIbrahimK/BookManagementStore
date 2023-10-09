package com.example.onlinebookstoremanagementsystem.Dtos;

import com.example.onlinebookstoremanagementsystem.Entities.Book;
import lombok.Data;

import java.util.List;

@Data
public class BookResponse {
    private String Message;
    private List<Book>books;
}
