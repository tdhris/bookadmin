package com.sap.internship.libraryadmin.service;

import javax.jws.WebService;
import javax.ws.rs.PathParam;

import com.sap.internship.libraryadmin.model.Book;

@WebService
public interface LoanService {

    public void takeBook(@PathParam("uder_id") long user_id, @PathParam("book_id") long book_id, Book book);

    void returnBook(long user_id, long book_id);
}
