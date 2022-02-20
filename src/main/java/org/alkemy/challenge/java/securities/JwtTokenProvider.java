package org.alkemy.challenge.java.securities;

import java.util.Date;

import org.alkemy.challenge.java.exceptions.DisneyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
    
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private Integer jwtExpirationMilliseconds;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationMilliseconds);
        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
        return token;
    }

    public String getUsernameOfJWT(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.SignatureException exception) {
            throw new DisneyException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        }
        catch (MalformedJwtException exception) {
            throw new DisneyException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        }
        catch (ExpiredJwtException exception) {
            throw new DisneyException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        }
        catch (UnsupportedJwtException exception) {
            throw new DisneyException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        }
        catch (IllegalArgumentException exception) {
            throw new DisneyException(HttpStatus.BAD_REQUEST, "Empty JWT claims");
        }
    }

}
