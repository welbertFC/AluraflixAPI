package br.com.challengebackend.aluraflixapi.security;

import br.com.challengebackend.aluraflixapi.models.UserClient;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    private String jwtSecret = "teste";

    private Long expiration = 180000L;

    private Long expirationRefreshToken = 86400000L;

    public String generateToken(UserClient user, HttpServletRequest request) {
        var algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getProfiles().stream().map(profile -> (GrantedAuthority) profile::getProfile).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public String generateRefreshToken(UserClient user, HttpServletRequest request) {
        var algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationRefreshToken))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
    }
}
