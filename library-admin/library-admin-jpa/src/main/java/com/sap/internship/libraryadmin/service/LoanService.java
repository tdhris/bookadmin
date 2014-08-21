package com.sap.internship.libraryadmin.service;

import java.util.Collection;

import javax.jws.WebService;
import javax.ws.rs.PathParam;

import com.sap.internship.libraryadmin.model.Book;
import com.sap.internship.libraryadmin.model.User;

@WebService
public interface LoanService {

    public void takeBook(@PathParam("user_id") long user_id, @PathParam("book_id") long book_id);

    public Collection<User> getCurrentBorrowers(@PathParam("book_id") long book_id);

    public Collection<User> getPastBorrowers(@PathParam("book_id") long book_id);

    public Collection<Book> getCurrentBooksOfUser(@PathParam("user_id") long user_id);

    public Collection<Book> getReturnedBooksOfUser(@PathParam("user_id") long user_id);

    void returnBook(@PathParam("user_id") long user_id, @PathParam("book_id") long book_id);
}
