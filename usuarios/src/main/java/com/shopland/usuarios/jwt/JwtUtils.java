package com.shopland.usuarios.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${spring.app.jwtSecret}")
    private String jwtSecret;

    @Value("${spring.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String getJwtFromHeader(HttpServletRequest request) {
        String baererToken = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", baererToken);
        if (baererToken != null && baererToken.startsWith("Bearer ")) {
            return baererToken.substring(7);
        }
        return null;
    }

    public String generateTokenFromUsernameWithRoles(UserDetails userDetails, String roles) {
        String username = userDetails.getUsername();
        return Jwts.builder()
                .subject(username)
                .claim("roles",
                        roles)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key())
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            System.out.println("Validate");
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: ", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token expired: ", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: ", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims String is empty: {}", e.getMessage());
        }
        return false;
    }

}
