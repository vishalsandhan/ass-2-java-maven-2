package com.example;

import com.example.LoginServlet;

import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

class LoginServletTest {

    @Test
    void testDoPost_validCredentials() throws Exception {
        // Mock objects
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        PrintWriter writer = mock(PrintWriter.class);

        // Stub methods
        when(request.getParameter("username")).thenReturn("testUser");
        when(request.getParameter("password")).thenReturn("testPassword");
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);

        // Test the doPost method
        LoginServlet servlet = new LoginServlet();
        servlet.doPost(request, response);

        // Verify session and redirection
        verify(session).setAttribute("username", "testUser");
        verify(response).sendRedirect("dashboard");
    }

    @Test
    public void testDoPost_invalidCredentials() throws Exception {
        // Mock objects
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        PrintWriter writer = mock(PrintWriter.class);

        // Stub methods
        when(request.getParameter("username")).thenReturn("wrongUser");
        when(request.getParameter("password")).thenReturn("wrongPassword");
        when(response.getWriter()).thenReturn(writer);

        // Test the doPost method
        LoginServlet servlet = new LoginServlet();
        servlet.doPost(request, response);

        // Verify invalid login behavior
        verify(writer).println(contains("Invalid credentials"));
    }
}
