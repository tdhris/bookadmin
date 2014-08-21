package com.sap.internship.libraryadmin.service;

import java.util.Collection;

import com.sap.internship.libraryadmin.model.Book;

public interface BookService {
    Collection<Book> getBooks();

    Book getBook(long id);

    void addBook(Book book);

    void updateBook(long id, Book book);

    void deleteBook(long id);

    int getCopiesTakenCount(long id);
}
