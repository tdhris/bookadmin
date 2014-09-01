package com.sap.internship.libraryadmin.logout;

import java.io.IOException;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sap.security.auth.login.LoginContextFactory;

public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRemoteUser() != null) {
            response.setContentType("text/html");
            try {
                LoginContext loginContext = LoginContextFactory.createLoginContext();
                loginContext.logout();
                response.setStatus(HttpServletResponse.SC_OK);
                // response.getWriter().println("You have successfully logged out.");

            } catch (LoginException e) {
                String errMsg = "Logout failed. Reason: " + e.getMessage();
                response.getWriter().println(errMsg);
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        } else {
            response.getWriter().println("You have successfully logged out.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}