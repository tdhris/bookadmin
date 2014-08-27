package com.sap.internship.libraryadmin.service;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

public class BaseService {

    protected Response okResponse(Object content) {
        ResponseBuilder rb = null;
        if (content != null) {
            rb = Response.ok();
        } else {
            rb = Response.ok(content);
        }

        return rb.build();
    }

    protected Response okResponse() {
        return okResponse(null);
    }

    protected Response customResponse(Status status, String message) {
        return Response.status(status).entity(message).build();
    }

    protected Response customResponse(Status status) {
        return Response.status(status).build();
    }
}
