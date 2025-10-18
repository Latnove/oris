package filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "deleteRestrict", urlPatterns = "/users")
public class RestrictPostFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (req.getMethod().equals("POST")) {
            res.setStatus(403);
            res.setContentType("text/plain; charset=UTF-8");
            res.getWriter().write("Включен фильтр, кнопка не работает");
            return;
        }
        chain.doFilter(req, res);
    }
}
