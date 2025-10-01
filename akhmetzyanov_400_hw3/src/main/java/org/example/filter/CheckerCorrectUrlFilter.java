package org.example.filter;

import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class CheckerCorrectUrlFilter extends HttpFilter {

    private final List<String> urlPatterns = Arrays.asList("/main.ftl", "/index.ftl", "/login.ftl", "/main.ftl", "/sign_up.ftl", "/users.ftl");

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, javax.servlet.ServletException {
        String path = req.getRequestURI().substring(req.getContextPath().length());
        boolean matches = urlPatterns.stream().anyMatch(pattern -> path.equals(pattern));

        if (matches) {
            System.out.println("Filter triggered for path: " + path);
            chain.doFilter(req, res);
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.setContentType("text/html;charset=UTF-8");
            res.getWriter().write("<html><body><h1>404 - Страница не найдена</h1></body></html>");
            res.getWriter().flush();
        }
    }
}