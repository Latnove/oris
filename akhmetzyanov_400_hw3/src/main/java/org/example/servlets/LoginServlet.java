package org.example.servlets;

import org.example.models.Database;
import org.example.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        if (session == null) {
            req.setAttribute("name", null);
        } else {
            req.setAttribute("name", session.getAttribute("name"));
        }

        req.getRequestDispatcher("login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = Database.getUser(login);

        if (user == null) {
            resp.sendRedirect("login.ftl");
            return;
        }

        if (user.getLogin().equalsIgnoreCase(login) && user.getPassword().equals(password)) {
            // session
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("name", user.getName());

            httpSession.setMaxInactiveInterval(60 * 60);

            // cookie
            Cookie cookie = new Cookie("name", user.getName());
            cookie.setMaxAge(24 * 60 * 60);

            resp.addCookie(cookie);
            req.setAttribute("user", user.getName());
            req.setAttribute("sessionId", httpSession.getId());
            req.setAttribute("cookieUser", cookie.getValue());
            req.getRequestDispatcher("main.ftl").forward(req, resp);
        } else {
            req.getRequestDispatcher("login.ftl").forward(req, resp);
        }
    }

}