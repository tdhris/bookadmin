package com.sap.internship.libraryadmin.service;

import javax.jws.WebService;
import javax.ws.rs.PathParam;

@WebService
public interface LoanService {

    public void takeBook(@PathParam("uder_id") long user_id, @PathParam("book_id") long book_id);

    void returnBook(long user_id, long book_id);
}
