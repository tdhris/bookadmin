package com.sap.internship.libraryadmin.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
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

@Stateless
@Path("/Loans")
public class LoanServiceImpl implements LoanService {
    @PersistenceContext(unitName = "UserService", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager = EntityManagerHelper.getEntityManager(DataSourceProvider.getInstance().get());

    @SuppressWarnings("unchecked")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<BookLoan> getLoans() {
        Query query = entityManager.createQuery("SELECT b FROM BookLoan b");
        return query.getResultList();
    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/take-book/{book_id}")
    public Response takeBook(@PathParam("user_id") long user_id, @PathParam("book_id") long book_id) {
        User user = entityManager.find(User.class, user_id);
        Book book = entityManager.find(Book.class, book_id);
        if (!book.hasAvailableCopies()) {
            return Response.status(Status.PRECONDITION_FAILED).entity("There are no available copies of this book").build();
        } else if (!user.canTakeBook(book)) {
            return Response.status(Status.PRECONDITION_FAILED).entity("User already has a copy of this book").build();
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
            return Response.status(Status.OK).build();
        }
    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/return-book/{book_id}")
    public Response returnBook(@PathParam("user_id") long user_id, @PathParam("book_id") long book_id) {
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
            return Response.status(Status.NOT_FOUND).build();
        } else {
            entityManager.getTransaction().begin();
            currentLoan.returnBook();
            book.removeLoan(currentLoan);
            book.returnCopy();
            entityManager.merge(currentLoan);
            entityManager.merge(book);
            entityManager.getTransaction().commit();
            return Response.status(Status.OK).build();
        }

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
    @Path("/users/{user_id}/current-loans")
    public Collection<BookLoan> getCurrentBookLoansOfUser(@PathParam("user_id") long user_id) {
        User user = entityManager.find(User.class, user_id);
        return user.getActiveBookLoans();
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/returned-loans")
    public Collection<BookLoan> getReturnedBookLoansOfUser(@PathParam("user_id") long user_id) {
        User user = entityManager.find(User.class, user_id);
        return user.getOldBookLoans();
    }

}
