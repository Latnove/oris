package org.example.servlets;

import org.example.entity.User;
import org.example.service.LoginService;
import org.example.service.impl.LoginServiceImpl;

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
    private final static LoginService loginService = new LoginServiceImpl();

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

        User user = loginService.getUser(login, password);

        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }


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
    }

}