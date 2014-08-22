package com.sap.internship.libraryadmin.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sap.internship.libraryadmin.model.Book;
import com.sap.internship.libraryadmin.model.BookLoan;
import com.sap.internship.libraryadmin.model.User;

@Stateless
@Path("/Loans")
@Produces(MediaType.APPLICATION_JSON)
@WebService
public class LoanServiceImpl implements LoanService {
    @PersistenceContext(unitName = "UserService", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager = EntityManagerHelper.getEntityManager(DataSourceProvider.getInstance().get());

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/take-book/{book_id}")
    public void takeBook(@PathParam("user_id") long user_id, @PathParam("book_id") long book_id) {
        User user = entityManager.find(User.class, user_id);
        Book book = entityManager.find(Book.class, book_id);
        if (book != null && user != null && book.hasAvailableCopies()) {
            entityManager.getTransaction().begin();
            BookLoan loan = new BookLoan();
            loan.setBook(book);
            loan.setUser(user);
            entityManager.merge(loan);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/books-now/")
    public Collection<Book> getBooksCurrentlyTakenByUser(@PathParam("user_id") long user_id) {
        Collection<Book> books = new ArrayList<>();

        User user = entityManager.find(User.class, user_id);
        Collection<BookLoan> loans = user.getBookLoans();
        for (BookLoan loan : loans) {
            if (loan.isActive()) {
                books.add(loan.getBook());
            }
        }

        return books;
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/books-returned/")
    public Collection<Book> getBooksReturnedByUser(long user_id) {
        Collection<Book> books = new ArrayList<>();

        User user = entityManager.find(User.class, user_id);
        Collection<BookLoan> loans = user.getBookLoans();
        for (BookLoan loan : loans) {
            if (!loan.isActive()) {
                books.add(loan.getBook());
            }
        }
        return books;
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{book_id}/users-active/")
    public Collection<User> getUsersWhoHaveBookCopyNow(long book_id) {
        Collection<User> users = new ArrayList<>();

        Book book = entityManager.find(Book.class, book_id);
        Collection<BookLoan> loans = book.getBookLoans();
        for (BookLoan loan : loans) {
            if (loan.isActive()) {
                users.add(loan.getUser());
            }
        }
        return users;
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{book_id}/users-returned/")
    public Collection<User> getUsersWhoHaveTakenBookBefore(@PathParam("book_id") long book_id) {
        Collection<User> users = new ArrayList<>();

        Book book = entityManager.find(Book.class, book_id);
        Collection<BookLoan> loans = book.getBookLoans();
        for (BookLoan loan : loans) {
            if (!loan.isActive()) {
                users.add(loan.getUser());
            }
        }
        return users;
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/return-book/bookid={book_id}")
    public void returnBook(@PathParam("uder_id") long user_id, @PathParam("book_id") long book_id) {
        // User user = entityManager.find(User.class, user_id);
        // Book book = entityManager.find(Book.class, book_id);
        // if (user.getBooks().contains(book)) {
        // // user.returnBook(book);
        // // book.removeBorrower(user);
        // entityManager.getTransaction().begin();
        // entityManager.merge(user);
        // entityManager.merge(book);
        // entityManager.getTransaction().commit();
        // }
    }

}
