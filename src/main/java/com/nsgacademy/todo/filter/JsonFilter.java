package com.nsgacademy.todo.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JsonFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        // Validate JSON for POST & PUT
        if ("POST".equalsIgnoreCase(req.getMethod()) ||
                "PUT".equalsIgnoreCase(req.getMethod())) {

            String contentType = req.getContentType();

            if (contentType == null || !contentType.contains("application/json")) {
                res.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                res.getWriter().write("""
                    {
                      "error": "Content-Type must be application/json"
                    }
                    """);
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
