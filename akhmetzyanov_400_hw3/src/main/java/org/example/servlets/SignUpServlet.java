package org.example.servlets;

import org.example.models.Database;
import org.example.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "SignUp", urlPatterns = "/sign_up")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendRedirect("sign_up.ftl");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // registration
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        User user = Database.getUser(login);

        // пользователь уже существует с таким логином, пустой логин, имя и пароль меньше 8
        if (user != null || login.equals("") || name.equals("") || password.length() < 8) {
            resp.sendRedirect("sign_up.ftl");
            return;
        }

        Database.addUser(login, name, password);

        // session
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("name", name);

        httpSession.setMaxInactiveInterval(60 * 60);

        // cookie
        Cookie cookie = new Cookie("name", name);
        cookie.setMaxAge(24 * 60 * 60);

        resp.addCookie(cookie);

        resp.sendRedirect("main.ftl");
    }
}