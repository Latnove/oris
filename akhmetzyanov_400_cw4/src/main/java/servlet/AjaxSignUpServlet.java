package servlet;

import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/ajax/sign_up")
public class AjaxSignUpServlet extends HttpServlet {
    UserService service = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        resp.setContentType("text/plain");
        if (login.isBlank()) {
            resp.getWriter().write("Введите логин");
            return;
        }
        if (service.getByLogin(login) != null) {
            resp.getWriter().write("Логин занят");
        } else {
            resp.getWriter().write("Логин не занят");
        }
    }
}
