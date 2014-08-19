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
import com.sap.internship.libraryadmin.model.User;

@Stateless
@Path("/Users")
@Produces(MediaType.APPLICATION_JSON)
@WebService
public class UserServiceImpl implements UserService {
    @PersistenceContext(unitName = "UserService", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager = EntityManagerHelper.getEntityManager(DataSourceProvider.getInstance().get());

    @SuppressWarnings("unchecked")
    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> getUsers() {
        Query query = entityManager.createQuery("SELECT u FROM User u");
        return query.getResultList();
    }

    @Override
    @GET
    @Path("/{id}")
    public User getUser(@PathParam("id") long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addUser(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void updateUser(long id, User user) {
        entityManager.merge(user);
    }

    @Override
    @DELETE
    @Path("/{id}")
    public void deleteUser(long id) {
        User user = getUser(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/books")
    public Collection<Book> getBooksTakenByUser(@PathParam("id") long id) {
        User user = entityManager.find(User.class, id);
        return user.getBooks();
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/books-returned")
    public Collection<Book> getBooksReturnedByUser(long id) {
        User user = entityManager.find(User.class, id);
        return user.getBooksReturned();
    }

}
