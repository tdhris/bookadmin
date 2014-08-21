package com.sap.internship.libraryadmin.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
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
            book.addLoan(loan);
            user.addLoan(loan);
            entityManager.merge(loan);
            entityManager.merge(book);
            entityManager.merge(user);
            entityManager.getTransaction().commit();
        }
    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/return-book/{book_id}")
    public void returnBook(@PathParam("user_id") long user_id, @PathParam("book_id") long book_id) {
        // User user = entityManager.find(User.class, user_id);
        // Book book = entityManager.find(Book.class, book_id);
        // if (user.getBooks().contains(book)) {
        // user.returnBook(book);
        // book.removeBorrower(user);
        // entityManager.getTransaction().begin();
        // entityManager.merge(user);
        // entityManager.merge(book);
        // entityManager.getTransaction().commit();
        // }
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/books/{book_id}/current-borrowers")
    public Collection<User> getCurrentBorrowers(@PathParam("book_id") long book_id) {
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
    @Path("/books/{book_id}/past-borrowers")
    public Collection<User> getPastBorrowers(@PathParam("book_id") long book_id) {
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
    @Path("/users/{user_id}/current-books")
    public Collection<Book> getCurrentBooksOfUser(@PathParam("user_id") long user_id) {
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
    @Path("/users/{user_id}/returned-books")
    public Collection<Book> getReturnedBooksOfUser(@PathParam("user_id") long user_id) {
        Collection<Book> books = new ArrayList<>();

        User user = entityManager.find(User.class, user_id);

        System.out.println(user.getBookLoans().size());
        System.out.println(user.getActiveBookLoans().size());

        Collection<BookLoan> loans = user.getBookLoans();
        for (BookLoan loan : loans) {
            if (!loan.isActive()) {
                books.add(loan.getBook());
            }
        }
        return books;
    }

}
