package com.example.rest_crud.controller;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private String id;
    private String title;
    private String author;
    private int year;
}
