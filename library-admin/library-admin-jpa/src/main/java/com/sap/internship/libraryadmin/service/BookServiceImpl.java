package com.sap.internship.libraryadmin.service;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sap.internship.libraryadmin.model.Book;

@Stateless
@Path("/Books")
@Produces(MediaType.APPLICATION_JSON)
@WebService
public class BookServiceImpl implements BookService {
    @PersistenceContext(unitName = "BookService", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager = EntityManagerHelper.getEntityManager(DataSourceProvider.getInstance().get());

    @SuppressWarnings("unchecked")
    @Override
    @GET
    public Collection<Book> getBooks() {
        Query query = entityManager.createQuery("SELECT b FROM Book b");
        return query.getResultList();
    }

    @Override
    @GET
    @Path("/{id}")
    public Book getBook(@PathParam("id") long id) {
        return entityManager.find(Book.class, id);
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public int getCopiesTakenCount(long id) {
        Book book = entityManager.find(Book.class, id);
        return book.takenCount();
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addBook(Book book) {
        String copies = book.getCopies();
        book.setAvailableCopies(copies);
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void updateBook(@PathParam("id") long id, Book book) {
        entityManager.merge(book);
    }

    @Override
    @DELETE
    @Path("/{id}")
    public void deleteBook(@PathParam("id") long id) {
        Book book = getBook(id);
        if (book != null) {
            entityManager.remove(book);
        }
    }

}
