package ru.geekbrains;

import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = "/user/*")
public class UserServlet extends HttpServlet {

    private static final Pattern PARAM_PATTERN = Pattern.compile("\\/(\\d+)");

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        this.userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Matcher matcher = PARAM_PATTERN.matcher(req.getPathInfo());
        if (!matcher.matches()) {
            resp.getWriter().println("<p>Bad request " + req.getPathInfo() + "</p>");
            resp.setStatus(400);
            return;
        }

        long id = Long.parseLong(matcher.group(1));
        User user = userRepository.findById(id);

        if (user == null) {
            resp.getWriter().println("<p>User not found</p>");
            resp.setStatus(404);
            return;
        }

        resp.getWriter().println("<p>Id: " + user.getId() + "</p>");
        resp.getWriter().println("<p>Username: " + user.getUsername() + "</p>");
    }
}
