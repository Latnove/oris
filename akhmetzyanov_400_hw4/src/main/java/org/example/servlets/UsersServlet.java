package org.example.servlets;

import org.example.dto.UserDto;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "Users", urlPatterns = "/users")
public class UsersServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDto> users = userService.getAll();
        if (users != null && !users.isEmpty()) {
            req.setAttribute("users", users);
        }
        req.getRequestDispatcher("users.ftl").forward(req, resp);
    }
}