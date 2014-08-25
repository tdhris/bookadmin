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

import com.sap.internship.libraryadmin.model.User;

@Path("/Users")
public class UserServiceImpl implements UserService {
    private EntityManagerProvider entityManagerProvider;

    public UserServiceImpl() {
        entityManagerProvider = EntityManagerProvider.getInstance();
    }

    @SuppressWarnings("unchecked")
    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> getUsers() {
        EntityManager entityManager = entityManagerProvider.get();
        Query query = entityManager.createQuery("SELECT u FROM User u");
        return query.getResultList();
    }

    @Override
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam("id") long id) {
        EntityManager entityManager = entityManagerProvider.get();
        return entityManager.find(User.class, id);
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addUser(User user) {
        EntityManager entityManager = entityManagerProvider.get();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();

    }

    @Override
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public void updateUser(long id, User user) {
        EntityManager entityManager = entityManagerProvider.get();
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    @Override
    @DELETE
    @Path("/{id}")
    public void deleteUser(long id) {
        User user = getUser(id);
        if (user != null) {
            EntityManager entityManager = entityManagerProvider.get();
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
        }
    }
}
