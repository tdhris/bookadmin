package com.sap.internship.libraryadmin.service;

import java.util.Collection;

import javax.jws.WebService;
import javax.ws.rs.PathParam;

import com.sap.internship.libraryadmin.model.Book;
import com.sap.internship.libraryadmin.model.User;

@WebService
public interface LoanService {

    public void takeBook(@PathParam("uder_id") long user_id, @PathParam("book_id") long book_id);

    public Collection<Book> getBooksCurrentlyTakenByUser(@PathParam("user_id") long user_id);

    public Collection<Book> getBooksReturnedByUser(@PathParam("user_id") long user_id);

    public Collection<User> getUsersWhoHaveTakenBookBefore(@PathParam("book_id") long book_id);

    public Collection<User> getUsersWhoHaveBookCopyNow(@PathParam("book_id") long book_id);

    void returnBook(long user_id, long book_id);
}
