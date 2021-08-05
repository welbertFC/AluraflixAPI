package br.com.challengebackend.aluraflixapi.security;

import br.com.challengebackend.aluraflixapi.dto.LoginRequest;
import br.com.challengebackend.aluraflixapi.dto.LoginResponse;
import br.com.challengebackend.aluraflixapi.models.UserClient;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Date;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        setAuthenticationFailureHandler(new AuthenticationFailureHandler());
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
        tokenResponse(response, accessToken, refreshToken);

    }

    public static void tokenResponse(HttpServletResponse response, String accessToken, String refreshToken) throws IOException {
        var loginResponse = new LoginResponse();
        loginResponse.setAuthorization(accessToken);
        loginResponse.setRefreshToken(refreshToken);

        var json = new ObjectMapper().writeValueAsString(loginResponse);

        response.setCharacterEncoding("UTF8");
        response.setContentType("application/json");
        response.addHeader("access-control-expose-headers", "Authorization");
        response.getWriter().append(json);
    }

    private class AuthenticationFailureHandler implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(
                HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": "
                    + date
                    + ", "
                    + "\"status\": 401, "
                    + "\"erro\": \"Não autorizado\", "
                    + "\"message\": \"Email ou senha inválidos\", "
                    + "\"path\": \"/login\"}";
        }
    }
}
