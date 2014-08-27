package com.sap.internship.libraryadmin.service;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.sap.internship.libraryadmin.model.LoggedUser;

@Path("/LoggedUser")
public class LoggedUserService extends BaseService {
    @Context
    private HttpServletRequest request;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public LoggedUser getLoggedInUser() {
        Principal userPrincipal = request.getUserPrincipal();
        String user = null;
        if (userPrincipal == null) {
            user = "Anonymous";
        } else {
            user = userPrincipal.getName();
        }
        return new LoggedUser(user);
    }
}
