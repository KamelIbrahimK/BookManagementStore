package com.example.onlinebookstoremanagementsystem.Services;

import com.example.onlinebookstoremanagementsystem.Dtos.AddBookDto;
import com.example.onlinebookstoremanagementsystem.Dtos.BookResponse;
import com.example.onlinebookstoremanagementsystem.Dtos.UpdateBookDto;
import com.example.onlinebookstoremanagementsystem.Dtos.UpdateBookResponse;
import com.example.onlinebookstoremanagementsystem.Entities.Book;
import com.example.onlinebookstoremanagementsystem.Entities.Customer;
import com.example.onlinebookstoremanagementsystem.Entities.History;
import com.example.onlinebookstoremanagementsystem.Enums.CategoryType;
import com.example.onlinebookstoremanagementsystem.Enums.HistoryType;
import com.example.onlinebookstoremanagementsystem.Repositories.BookRepo;
import com.example.onlinebookstoremanagementsystem.Repositories.CustomerRepo;
import com.example.onlinebookstoremanagementsystem.Repositories.HistoryRepo;
import com.example.onlinebookstoremanagementsystem.Utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;
    @Autowired
    private HistoryRepo historyRepo;
    @Autowired
    private CustomerRepo customerRepo;


    public BookResponse addBook(List<AddBookDto> books) {
        List<Book> booksToBeSaved = new ArrayList<>();
        BookResponse bookResponse = new BookResponse();
        for (int i = 0; i < books.size(); i++) {
            Book book = new Book(books.get(i).getName(), books.get(i).getAuthorName(), books.get(i).getCategoryType());
            booksToBeSaved.add(book);
        }
        bookRepo.saveAll(booksToBeSaved);
        bookResponse.setBooks(booksToBeSaved);
        bookResponse.setMessage(Constants.SUCCESS_BOOK_STORAGE);
        return bookResponse;
    }

    public UpdateBookResponse updateBook(UpdateBookDto updateBookDto) {
        Optional<Book> optionalBook = bookRepo.findById(updateBookDto.getId());
        UpdateBookResponse response = new UpdateBookResponse();
        if (optionalBook.isPresent()) {
            Book oldBook = optionalBook.get();
            oldBook.setName(updateBookDto.getName());
            oldBook.setAuthorName(updateBookDto.getAuthorName());
            oldBook.setAvailable(updateBookDto.getAvailable());
            oldBook.setCategoryType(updateBookDto.getCategoryType());
            bookRepo.save(oldBook);
            response.setBook(oldBook);
            response.setMessage(Constants.SUCCESS_BOOK_STORAGE);
            return response;
        } else {
            response.setMessage(Constants.UNAVAILABLE_BOOK);
            return response;
        }
    }

    public BookResponse getAllBooks() {
        BookResponse response = new BookResponse();
        List<Book> books = bookRepo.findAll();
        if (books.isEmpty()) {
            response.setMessage(Constants.EMPTY_BOOKS);
            return response;
        } else {
            response.setBooks(books);
            response.setMessage(Constants.AVAILABLE_BOOKS);
            return response;
        }
    }

    public BookResponse findAllBooksByCategory(CategoryType categoryType, Integer userId) {
        BookResponse response = new BookResponse();
        List<Book> books = bookRepo.findByCategoryType(categoryType);

        if (books.isEmpty()) {
            response.setMessage(Constants.EMPTY_BOOKS);
            return response;
        } else {
            if (userId != null) {
                Customer customer = customerRepo.findByCustomerUserId(userId);
                if (customer != null) {
                    response.setBooks(books);
                    response.setMessage(Constants.AVAILABLE_BOOKS);

                    History history = new History(customer.getId(), books.get(0).getId(), HistoryType.BROWSE);
                    historyRepo.save(history);

                } else {
                    response.setMessage(Constants.CUSTOMER_NOT_FOUND);

                }
            } else {
                //if userId==null -> that's mean he is an admin
                response.setBooks(books);
                response.setMessage(Constants.AVAILABLE_BOOKS);


            }
        }

        return response;
    }

    public BookResponse findBookById(Integer id, Integer userId) {
        BookResponse response = new BookResponse();
        Optional<Book> book = bookRepo.findById(id);
        if (book.isPresent()) {
            response.setBooks(Arrays.asList(book.get()));
            response.setMessage(Constants.BOOK_RETURN);
            if (userId != null) {
                Customer customer = customerRepo.findByCustomerUserId(userId);
                History history = new History(customer.getId(), id, HistoryType.BROWSE);
                historyRepo.save(history);
            }
            return response;
        } else {
            response.setMessage(Constants.EMPTY_BOOKS);
            return response;
        }
    }

    public BookResponse deleteBookById(Integer id) {
        BookResponse response = new BookResponse();
        boolean isExist = bookRepo.existsById(id);
        if (isExist) {
            bookRepo.deleteById(id);
            response.setMessage(Constants.Book_DELETED);
            return response;
        } else {
            response.setMessage(Constants.UNAVAILABLE_BOOK);
            return response;
        }
    }

    public BookResponse borrowBook(Integer id, Integer userId) {
        BookResponse response = new BookResponse();
        Optional<Book> requestedBook = bookRepo.findById(id);
        if (requestedBook.isPresent() && requestedBook.get().getAvailable()) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime oneWeekLater = now.plusWeeks(1);
            requestedBook.get().setBorrowingDate(now);
            requestedBook.get().setReturnDate(oneWeekLater);
            requestedBook.get().setAvailable(false);
            bookRepo.save(requestedBook.get());
            if (userId != null) {
                Customer customer = customerRepo.findByCustomerUserId(userId);
                History history = new History(customer.getId(), requestedBook.get().getId(), HistoryType.BORROW);
                historyRepo.save(history);
            }
            response.setBooks(Arrays.asList(requestedBook.get()));
            response.setMessage(Constants.BORROW_BOOK);
            return response;
        } else {
            response.setMessage(Constants.UNAVAILABLE_BOOK);
            return response;
        }
    }

    public BookResponse returnBook(Integer id) {
        BookResponse response = new BookResponse();
        Optional<Book> returnedBook = bookRepo.findById(id);
        if (returnedBook.isPresent()) {
            if (returnedBook.get().getAvailable() == false) {
                returnedBook.get().setBorrowingDate(null);
                returnedBook.get().setReturnDate(LocalDateTime.now());
                returnedBook.get().setAvailable(true);
                bookRepo.save(returnedBook.get());
                response.setBooks(Arrays.asList(returnedBook.get()));
                response.setMessage(Constants.RETURN_BOOK);
                return response;
            }
            response.setMessage(Constants.NO_ONE_BORROW_THIS_BOOK);
            return response;
        }
        response.setMessage(Constants.UNAVAILABLE_BOOK);
        return response;
    }

    public BookResponse recommendBooksForUser(Integer userId) {
        if (userId != null) {
            Customer customer = customerRepo.findByCustomerUserId(userId);
            List<History> historyList = historyRepo.findByCustomerId(customer.getId());
            Map<String, Integer> CategoriesOccurrencesMap = new HashMap<>();

            Set<Integer> booksIds=historyList.stream().map(history -> history.getBookId()).collect(Collectors.toSet());
            List<Book> historyBooks= bookRepo.findByIdIn(booksIds);
            for (Book book : historyBooks) {
                if (CategoriesOccurrencesMap.containsKey(book.getCategoryType().name())) {
                    Integer oldCategoryCount = CategoriesOccurrencesMap.get(book.getCategoryType().name());
                    CategoriesOccurrencesMap.put(book.getCategoryType().name(), oldCategoryCount + 1);
                } else {
                    CategoriesOccurrencesMap.put(book.getCategoryType().name(), 1);
                }
            }
            Integer maxCategoryTypeOcc = 0;
            String maxCategoryTypeName = "";
            for (String key : CategoriesOccurrencesMap.keySet()) {
                if (CategoriesOccurrencesMap.get(key) > maxCategoryTypeOcc) {
                    maxCategoryTypeOcc = CategoriesOccurrencesMap.get(key);
                    maxCategoryTypeName = key;
                }
            }
            if (maxCategoryTypeName != "") {
                List<Book> recommendedBooks = bookRepo.findByCategoryType(CategoryType.valueOf(maxCategoryTypeName));
                BookResponse result = new BookResponse();
                result.setBooks(recommendedBooks);
                result.setMessage(Constants.RECOMMENDED_BOOKS);
                return result;
            } else {
                return getAllBooks();
            }

        }

        return getAllBooks();
    }
}


