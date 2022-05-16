package com.example.rest_crud.controller;

import com.example.rest_crud.dto.BookRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/book")
public class BookController {
    private ConcurrentHashMap<String, Book> books;

    public BookController() {
        books = new ConcurrentHashMap<>();
        books.put("0X-13", new Book("0X-13", "TheBook1", "Author1", 1994));
        books.put("0X-15", new Book("0X-15", "TheBook2", "Author2", 1995));
        books.put("0X-17", new Book("0X-17", "TheBook3", "Author3", 1996));
        books.put("0X-19", new Book("0X-19", "TheBook4", "Author4", 1997));
    }

    @GetMapping
    public List<Book> getBooks() {
        return books.values().stream().toList();
    }

    @PostMapping
    public Book createBook(@RequestBody BookRequest bookRequest) {
        String uuid = UUID.randomUUID().toString();
        Book newBook = new Book(uuid, bookRequest.title(), bookRequest.author(), bookRequest.year());
        books.put(uuid, newBook);

        return newBook;
    }

    @GetMapping(value="/{id}")
    public Book getBooksByID(@PathVariable("id") String id) {
        return books.get(id);
    }

    @PutMapping(value="/{id}")
    public Book updateBookByID(@PathVariable("id") String id, @RequestBody BookRequest bookRequest){
        Book updateBook = new Book(id, bookRequest.title(), bookRequest.author(), bookRequest.year());
        books.put(id,updateBook);
        return updateBook;
    }

    @DeleteMapping(value = "/{id}")
    public String deleteBookByID(@PathVariable("id") String id){
        books.remove(id);

        return "this book has been removed";
    }
}
