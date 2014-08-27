package com.sap.internship.libraryadmin.service;

import java.util.Collection;

import javax.persistence.EntityManager;
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
import javax.ws.rs.core.Response;

import com.sap.internship.libraryadmin.model.Book;
import com.sap.internship.libraryadmin.providers.EntityManagerProvider;

@Path("/Books")
public class BookServiceImpl extends BaseService implements BookService {
    private EntityManagerProvider managerProvider;

    public BookServiceImpl() {
        managerProvider = EntityManagerProvider.getInstance();
    }

    @SuppressWarnings("unchecked")
    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Book> getBooks() {
        EntityManager entityManager = managerProvider.get();
        Query query = entityManager.createQuery("SELECT b FROM Book b");
        return query.getResultList();
    }

    @Override
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getBook(@PathParam("id") long id) {
        EntityManager entityManager = managerProvider.get();
        return entityManager.find(Book.class, id);
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public int getCopiesTakenCount(long id) {
        EntityManager entityManager = managerProvider.get();
        Book book = entityManager.find(Book.class, id);
        return book.takenCount();
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(Book book) {
        EntityManager entityManager = managerProvider.get();
        String copies = book.getCopies();
        book.setAvailableCopies(copies);
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
        return this.okResponse();
    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void updateBook(@PathParam("id") long id, Book book) {
        EntityManager entityManager = managerProvider.get();
        entityManager.getTransaction().begin();
        entityManager.merge(book);
        entityManager.getTransaction().commit();
    }

    @Override
    @DELETE
    @Path("/{id}")
    public void deleteBook(@PathParam("id") long id) {
        Book book = getBook(id);
        EntityManager entityManager = managerProvider.get();
        if (book != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(book);
            entityManager.getTransaction().commit();
        }
    }

}
