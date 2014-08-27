package com.sap.internship.libraryadmin.service;

import java.util.Collection;

import javax.jws.WebService;
import javax.ws.rs.core.Response;

import com.sap.internship.libraryadmin.model.User;

@WebService
public interface UserService {
    Collection<User> getUsers();

    User getUser(long id);

    Response addUser(User user);

    void updateUser(long id, User user);

    void deleteUser(long id);
}
