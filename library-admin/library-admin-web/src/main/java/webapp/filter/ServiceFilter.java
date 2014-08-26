package main.java.webapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.sap.internship.libraryadmin.service.EntityManagerProvider;

public class ServiceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try {
            EntityManagerProvider.getInstance().initialize();
            chain.doFilter(req, resp);
        } finally {
            EntityManagerProvider.getInstance().closeEntityManager();
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

}
