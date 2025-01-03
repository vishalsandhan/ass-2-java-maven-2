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

    // Define constants for repetitive literals
    private static final String TEXT_HTML = "text/html";
    private static final String BODY_HTML_END = "</body></html>";
    private static final String USERNAME = "username";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(TEXT_HTML);
        try (PrintWriter out = response.getWriter()) {
            out.println("<html><head><title>Login Page</title></head><body>");
            out.println("<h1>Login Page</h1>");
            out.println("<form action='login' method='post'>");
            out.println("<label for='username'>Username:</label>");
            out.println("<input type='text' id='username' name='username'><br><br>");
            out.println("<label for='password'>Password:</label>");
            out.println("<input type='password' id='password' name='password'><br><br>");
            out.println("<button type='submit' id='loginButton'>Login</button>");
            out.println(BODY_HTML_END);
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating response");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(USERNAME);
        String password = request.getParameter("password");

        if ("testUser".equals(username) && "testPassword".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute(USERNAME, username);
            try {
                response.sendRedirect("dashboard");
            } catch (IOException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Redirection failed");
            }
        } else {
            response.setContentType(TEXT_HTML);
            try (PrintWriter out = response.getWriter()) {
                out.println("<html><head><title>Login Page</title></head><body>");
                out.println("<h1>Invalid credentials, please try again.</h1>");
                out.println("<a href='login'>Go back to Login</a>");
                out.println(BODY_HTML_END);
            } catch (IOException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating response");
            }
        }
    }

    @WebServlet("/dashboard")
    public static class DashboardServlet extends HttpServlet {
        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession(false);
            response.setContentType(TEXT_HTML);

            try (PrintWriter out = response.getWriter()) {
                if (session != null && session.getAttribute(USERNAME) != null) {
                    out.println("<html><head><title>Dashboard</title></head><body>");
                    out.println("<h1>Welcome to the Dashboard</h1>");
                    out.println("<p>Hello, " + session.getAttribute(USERNAME) + "!</p>");
                    out.println(BODY_HTML_END);
                } else {
                    try {
                        response.sendRedirect("login");
                    } catch (IOException e) {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Redirection failed");
                    }
                }
            } catch (IOException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating response");
            }
        }
    }
}
