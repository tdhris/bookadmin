package com.sap.internship.libraryadmin.service;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sap.internship.libraryadmin.model.Book;

@Stateless
@Path("/Books")
public class BookServiceImpl implements BookService {
	@PersistenceContext(unitName = "BookService", type = PersistenceContextType.TRANSACTION)
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("Books/")
	public Collection<Book> getBooks() {
		Query query = entityManager.createQuery("SELECT b FROM Book b");
		return (Collection<Book>) query.getResultList();
	}

	@Override
	@GET
	@Produces(MediaType.APPLICATION_XML)
	@Path("Books/{id}")
	public Book getBook(@PathParam("id") long id) {
		return entityManager.find(Book.class, id);
	}

	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public void addBook(Book book) {
		entityManager.persist(book);
	}

	@Override
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	@Path("Books/{id}")
	public void updateBook(@PathParam("id") long id, Book book) {
		entityManager.merge(book);
	}

	@Override
	@DELETE
	@Path("Books/{id}")
	public void deleteBook(@PathParam("id") long id) {
		Book book = getBook(id);
		if (book != null) {
			entityManager.remove(book);
		}
	}

}
