package com.avilysilva.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.avilysilva.todolist.users.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
        var servletPath = request.getServletPath();

        if (servletPath.equals("/tasks/")) {
            var authorization = request.getHeader("Authorization");

            var authEncoded = authorization.substring("Basic".length()).trim();
            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
            var authString = new String(authDecoded);

            String[] credentials = authString.split(":");

            String username = credentials[0];
            String password = credentials[1];

            var user = userRepository.findByUsername(username);

            if (user == null) response.sendError(401);

            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword().toCharArray());

            if (!passwordVerify.verified)  response.sendError(401);

            request.setAttribute("idUser", user.getId());

            filterChain.doFilter(request, response);
        }

        filterChain.doFilter(request, response);
    }

}
