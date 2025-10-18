package org.example.servlets;

import org.example.entity.User;
import org.example.service.LoginService;
import org.example.service.SignUpService;
import org.example.service.impl.LoginServiceImpl;
import org.example.service.impl.SignUpServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "SignUp", urlPatterns = "/sign_up")
public class SignUpServlet extends HttpServlet {
    LoginService loginService = new LoginServiceImpl();
    SignUpService signUpService = new SignUpServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("sign_up.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // registration
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String lastname = req.getParameter("name");

        if (!signUpService.saveUser(new User(0, name, lastname, login, password))) {
            req.getRequestDispatcher("sign_up.ftl").forward(req, resp);
            return;
        }

        // session
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("name", name);

        httpSession.setMaxInactiveInterval(60 * 60);

        // cookie
        Cookie cookie = new Cookie("name", name);
        cookie.setMaxAge(24 * 60 * 60);

        resp.addCookie(cookie);
        req.setAttribute("user", name);
        req.setAttribute("sessionId", httpSession.getId());
        req.setAttribute("cookieUser", cookie.getValue());
        req.getRequestDispatcher("main.ftl").forward(req, resp);
    }
}