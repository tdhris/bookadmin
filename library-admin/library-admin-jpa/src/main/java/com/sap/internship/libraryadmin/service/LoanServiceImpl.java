package com.sap.internship.libraryadmin.service;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sap.internship.libraryadmin.model.Book;
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
    @Path("/users/{user_id}/take-book/bookid={book_id}")
    public void takeBook(@PathParam("uder_id") long user_id, @PathParam("book_id") long book_id, Book book) {
        User user = entityManager.find(User.class, user_id);
        if (book.hasAvailableCopies()) {
            user.borrowBook(book);
            book.borrowBook(user);
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.merge(book);
            entityManager.getTransaction().commit();
            System.out.println("Added");
        }
        System.out.println("finished");
    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/users/{user_id}/return-book/bookid={book_id}")
    public void returnBook(@PathParam("uder_id") long user_id, @PathParam("book_id") long book_id) {
        User user = entityManager.find(User.class, user_id);
        Book book = entityManager.find(Book.class, book_id);
        if (user.getBooks().contains(book)) {
            user.returnBook(book);
            book.removeBorrower(user);
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.merge(book);
            entityManager.getTransaction().commit();
        }
    }

}
