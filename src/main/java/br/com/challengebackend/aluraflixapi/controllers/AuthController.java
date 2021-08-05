package br.com.challengebackend.aluraflixapi.controllers;

import br.com.challengebackend.aluraflixapi.security.AuthenticationFilter;
import br.com.challengebackend.aluraflixapi.security.JWTUtil;
import br.com.challengebackend.aluraflixapi.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@Api(tags = "Refresh Token")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final UserService userService;

    @GetMapping("/refresh_token")
    @ApiOperation(value = "Refresh token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            var refreshToken = authorizationHeader.substring("Bearer ".length());
            var accessToken = jwtUtil.generateNewTokenByRefreshToken(refreshToken, request, userService);
            AuthenticationFilter.tokenResponse(response, accessToken, refreshToken);
        }
    }
}
