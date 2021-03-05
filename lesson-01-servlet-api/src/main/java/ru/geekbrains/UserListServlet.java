package ru.geekbrains;

import ru.geekbrains.persist.User;
import ru.geekbrains.persist.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = "/users")
public class UserListServlet extends HttpServlet {

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        this.userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        PrintWriter wr = resp.getWriter();
//        wr.println("<table>");
//
//        wr.println("<tr>");
//        wr.println("<th>Id</th>");
//        wr.println("<th>Username</th>");
//        wr.println("</tr>");
//        for (User user : userRepository.findAll()) {
//            wr.println("<tr>");
//
//            wr.println("<td>" + user.getId() + "</td>");
//            wr.println("<td> <a href='" + getServletContext().getContextPath() + "/user/" + user.getId()  + "'>" + user.getUsername() + "</a></td>");
//
//            wr.println("</tr>");
//        }
//        wr.println("</table>");
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = userRepository.findAll();
        req.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/users.jsp").forward(req, resp);
    }
}
