package br.com.challengebackend.aluraflixapi.security;

import br.com.challengebackend.aluraflixapi.dto.LoginRequest;
import br.com.challengebackend.aluraflixapi.dto.LoginResponse;
import br.com.challengebackend.aluraflixapi.models.UserClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            var user = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
            var authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getSenha());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        var user = (UserClient) authentication.getPrincipal();
        var accessToken = jwtUtil.generateToken(user, request);
        var refreshToken = jwtUtil.generateRefreshToken(user, request);
        var loginResponse = new LoginResponse("Bearer " + accessToken, "Bearer " + refreshToken);

        var json = new ObjectMapper().writeValueAsString(loginResponse);

        response.setCharacterEncoding("UTF8");
        response.setContentType("application/json");
        response.addHeader("access-control-expose-headers", "Authorization");
        response.getWriter().append(json);

    }
}
