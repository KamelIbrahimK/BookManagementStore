package com.example.onlinebookstoremanagementsystem.Controllers;

import com.example.onlinebookstoremanagementsystem.Dtos.*;
import com.example.onlinebookstoremanagementsystem.Entities.Book;
import com.example.onlinebookstoremanagementsystem.Enums.CategoryType;
import com.example.onlinebookstoremanagementsystem.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(path = "/Book")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping(path = "/addNewBook")
    public BookResponse addBook(@RequestBody List<AddBookDto> addBookDto) {
        return bookService.addBook(addBookDto);
    }

    @PutMapping(path = "/updateBookDetails")
    public UpdateBookResponse updateBook(@RequestBody UpdateBookDto updateBookDto) {
        return bookService.updateBook(updateBookDto);
    }

    @GetMapping(path = "/findAllBooks")
    public BookResponse getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping(path = "/findAllBooksByCategory/{category}")
    public BookResponse findAllBooksByCategory(@PathVariable(value = "category" ) CategoryType categoryType, @RequestHeader( name = "userId", required = false) Integer userId) {
        return bookService.findAllBooksByCategory(categoryType,userId);
    }

    @GetMapping(path = "/findBookById/{id}")
    public BookResponse findBookById(@PathVariable("id") Integer id , @RequestHeader("userId") Integer userId) {
        return bookService.findBookById(id,userId);
    }

    @GetMapping(path = "/deleteBookById/{id}")
    public BookResponse deleteBookById(@PathVariable("id") Integer id) {
        return bookService.deleteBookById(id);
    }
    @PostMapping(path = "/borrowBook/{id}")
    public BookResponse borrowBook(@PathVariable("id")Integer id , @RequestHeader("userId") Integer userId){
        return bookService.borrowBook(id,userId);
    }
    @PostMapping(path = "/returnBook/{id}")
    public BookResponse returnBook(@PathVariable("id")Integer id ){
        return bookService.returnBook(id);
    }

    @PostMapping(path = "/recommendation/{userId}")
    public BookResponse recommendBooksForUser(@PathVariable("userId") Integer userId){
        return bookService.recommendBooksForUser(userId);
    }
}
