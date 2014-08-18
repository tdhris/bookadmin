package main.webapp;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sap.internship.libraryadmin.service.BookServiceImpl;

public class RestConfig extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public RestConfig() {
        singletons.add(new JacksonJsonProvider());
        singletons.add(new BookServiceImpl());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
