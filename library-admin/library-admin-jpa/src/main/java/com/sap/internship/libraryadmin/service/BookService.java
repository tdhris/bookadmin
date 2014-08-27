package com.sap.internship.libraryadmin.service;

import java.util.Collection;

import javax.ws.rs.core.Response;

import com.sap.internship.libraryadmin.model.Book;

public interface BookService {
    Collection<Book> getBooks();

    Book getBook(long id);

    Response addBook(Book book);

    void updateBook(long id, Book book);

    void deleteBook(long id);

    int getCopiesTakenCount(long id);
}
