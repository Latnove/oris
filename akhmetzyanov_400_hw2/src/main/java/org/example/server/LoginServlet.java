package org.example.server;

import org.example.models.Database;
import org.example.models.User;

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = Database.getUser(login);

        if (user == null) {
            resp.sendRedirect("/login");
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

            resp.sendRedirect("/main.jsp");
        } else {
            resp.sendRedirect("login.html");
        }
    }

}