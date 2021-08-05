package br.com.challengebackend.aluraflixapi.security;

import br.com.challengebackend.aluraflixapi.models.UserClient;
import br.com.challengebackend.aluraflixapi.services.UserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JWTUtil {

    private static String jwtSecret;

    private static Long expiration;

    private static Long expirationRefreshToken;

    public String generateToken(UserClient user, HttpServletRequest request) {
        var algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
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

    public String generateNewTokenByRefreshToken(String refreshToken, HttpServletRequest request, UserService userService) {
        try {
            var decodedJwt = validationToken(refreshToken);
            var userEmail = decodedJwt.getSubject();
            var user = userService.findByEmail(userEmail);
            return generateToken(user, request);
        } catch (Exception exception) {
            throw new AccessDeniedException(exception.getMessage(), exception);
        }

    }

    public DecodedJWT validationToken(String token) {
        try {
            var algorithm = Algorithm.HMAC256(jwtSecret.getBytes(StandardCharsets.UTF_8));
            var verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (Exception exception) {
            throw new AccessDeniedException(exception.getMessage(), exception);
        }

    }

    @Value("${JWT_SECRET}")
    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Value("${jwt.expiration}")
    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    @Value("${jwt.refresh.expiration}")
    public void setExpirationRefreshToken(Long expirationRefreshToken) {
        this.expirationRefreshToken = expirationRefreshToken;
    }
}
