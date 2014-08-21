package com.sap.internship.libraryadmin.service;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class ErrorHandler implements ExceptionMapper<Throwable> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorHandler.class);

    @Resource
    protected HttpServletRequest request;

    public ErrorHandler() {
    }

    public ErrorHandler(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Response toResponse(Throwable throwable) {
        LOGGER.error("Exception while processing service request to " + request.getRequestURL(), throwable);
        String message = throwable.getClass().getName() + ", error message: " + throwable.getMessage();
        return Response.status(INTERNAL_SERVER_ERROR).type("text/plain").entity(message).build();
    }

}