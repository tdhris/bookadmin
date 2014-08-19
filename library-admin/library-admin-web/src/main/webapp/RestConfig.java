package main.webapp;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.sap.internship.libraryadmin.service.BookServiceImpl;
import com.sap.internship.libraryadmin.service.LoanServiceImpl;
import com.sap.internship.libraryadmin.service.UserServiceImpl;

public class RestConfig extends Application {
    private Set<Object> singletons = new HashSet<Object>();

    public RestConfig() {
        singletons.add(new JacksonJsonProvider());
        singletons.add(new BookServiceImpl());
        singletons.add(new UserServiceImpl());
        singletons.add(new LoanServiceImpl());
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
