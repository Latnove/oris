package org.example.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "Main", urlPatterns = "/main")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sessionUser = (String) req.getSession().getAttribute("name");
        String cookieUser = "";
        String sessionId = "";

        if (sessionUser == null) {
            resp.sendRedirect("/login.ftl");
            return;
        }

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("name".equalsIgnoreCase(c.getName())) {
                    cookieUser = c.getValue();
                } else if ("jsessionid".equalsIgnoreCase(c.getName())) {
                    sessionId = c.getValue();
                }
            }
        } else {
            sessionId = req.getSession().getId();
        }

        System.out.println("dsfsd");
        req.setAttribute("user", "1");
        req.setAttribute("sessionId", sessionId);
        req.setAttribute("cookieUser", cookieUser);
        req.getRequestDispatcher("main.ftl").forward(req, resp);
    }
}
