package org.example.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.example.entity.User;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = "/ajax/sign_up")
public class AjaxSignUpServlet extends HttpServlet {
	UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain");
		String login = req.getParameter("login");
		if (login.isBlank()) {
			resp.getWriter().write("Введите логин");
			return;
		}

		User user = userService.getByLogin(login);

		if (user == null) {
			resp.getWriter().write("Данный логин не занят");
		} else {
			resp.getWriter().write("Данный логин занят другим пользователем");
		}
        
	}
}
   