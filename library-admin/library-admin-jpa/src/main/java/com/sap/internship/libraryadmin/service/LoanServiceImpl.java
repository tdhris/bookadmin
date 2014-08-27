package com.sap.internship.libraryadmin.service;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sap.internship.libraryadmin.model.Book;
import com.sap.internship.libraryadmin.model.BookLoan;
import com.sap.internship.libraryadmin.model.User;
import com.sap.internship.libraryadmin.providers.EntityManagerProvider;

@Path("/Loans")
public class LoanServiceImpl extends BaseService implements LoanService {
    private EntityManagerProvider entityManagerProvider;

    public LoanServiceImpl() {
        entityManagerProvider = EntityManagerProvider.getInstance();
    }

    @SuppressWarnings("unchecked")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<BookLoan> getLoans() {
        EntityManager entityManager = entityManagerProvider.get();
        Query query = entityManager.createQuery("SELECT b FROM BookLoan b");
        return query.getResultList();
    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/take-book/{book_id}")
    public Response takeBook(@PathParam("user_id") long user_id, @PathParam("book_id") long book_id) {
        EntityManager entityManager = entityManagerProvider.get();
        User user = entityManager.find(User.class, user_id);
        Book book = entityManager.find(Book.class, book_id);
        if (!book.hasAvailableCopies()) {
            return this.customResponse(Status.PRECONDITION_FAILED, "There are no available copies of this book");
        } else if (!user.canTakeBook(book)) {
            return this.customResponse(Status.PRECONDITION_FAILED, "User already has a copy of this book");
        } else {
            entityManager.getTransaction().begin();
            BookLoan loan = new BookLoan();
            loan.setBook(book);
            loan.setUser(user);
            user.addLoan(loan);
            book.addLoan(loan);
            entityManager.merge(loan);
            entityManager.merge(book);
            entityManager.merge(user);
            entityManager.getTransaction().commit();
            return this.okResponse();
        }
    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/return-book/{book_id}")
    public Response returnBook(@PathParam("user_id") long user_id, @PathParam("book_id") long book_id) {
        EntityManager entityManager = entityManagerProvider.get();
        User user = entityManager.find(User.class, user_id);
        Book book = entityManager.find(Book.class, book_id);
        BookLoan currentLoan = null;
        Collection<BookLoan> loans = user.getActiveBookLoans();
        for (BookLoan loan : loans) {
            if (book.equals(loan.getBook())) {
                currentLoan = loan;
            }
        }
        if (currentLoan == null) {
            return this.customResponse(Status.NOT_FOUND);
        } else {
            entityManager.getTransaction().begin();
            currentLoan.returnBook();
            book.removeLoan(currentLoan);
            book.returnCopy();
            entityManager.merge(currentLoan);
            entityManager.merge(book);
            entityManager.getTransaction().commit();
            return this.okResponse();
        }

    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/current-loans")
    public Collection<BookLoan> getCurrentBookLoansOfUser(@PathParam("user_id") long user_id) {
        EntityManager entityManager = entityManagerProvider.get();
        User user = entityManager.find(User.class, user_id);
        return user.getActiveBookLoans();
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/returned-loans")
    public Collection<BookLoan> getReturnedBookLoansOfUser(@PathParam("user_id") long user_id) {
        EntityManager entityManager = entityManagerProvider.get();
        User user = entityManager.find(User.class, user_id);
        return user.getOldBookLoans();
    }

}
