package com.avilysilva.todolist.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authorization = request.getHeader("Authorization");

        var authEncoded = authorization.substring("Basic".length()).trim();
        byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
        var authString = new String(authDecoded);

        String[] credentials = authString.split(":");

        String username = credentials[0];
        String password = credentials[1];

        System.out.println(authString);

        filterChain.doFilter(request, response);
    }

}
