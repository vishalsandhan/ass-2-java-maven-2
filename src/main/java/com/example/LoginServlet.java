package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Login Page</title></head><body>");
        out.println("<h1>Login Page</h1>");
        out.println("<form action='login' method='post'>");
        out.println("<label for='username'>Username:</label>");
        out.println("<input type='text' id='username' name='username'><br><br>");
        out.println("<label for='password'>Password:</label>");
        out.println("<input type='password' id='password' name='password'><br><br>");
        out.println("<button type='submit' id='loginButton'>Login</button>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Simple validation for username and password
        if ("testUser".equals(username) && "testPassword".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            response.sendRedirect("dashboard");
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Login Page</title></head><body>");
            out.println("<h1>Invalid credentials, please try again.</h1>");
            out.println("<a href='login'>Go back to Login</a>");
            out.println("</body></html>");
        }
    }

    @WebServlet("/dashboard")
    public static class DashboardServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession(false);
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            if (session != null && session.getAttribute("username") != null) {
                out.println("<html><head><title>Dashboard</title></head><body>");
                out.println("<h1>Welcome to the Dashboard</h1>");
                out.println("<p>Hello, " + session.getAttribute("username") + "!</p>");
                out.println("</body></html>");
            } else {
                response.sendRedirect("login");
            }
        }
    }
}
