package com.example.blogPro.security;

import com.example.blogPro.exception.BlogAPIException;
import io.jsonwebtoken.*;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpirationInMs;

    //geneate token

    public String generateToken(Authentication authentication){
        String userName =  authentication.getName();
        Date currentDate = new Date();
        Date expDate = new Date(currentDate.getTime() +jwtExpirationInMs );

        String token = Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();

        return token;
    }
    public String getUserFromJWT(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJwt(token).getBody();
        return  claims.getSubject();
    }

    //validate jwt token
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJwt(token);
            return true;
        }catch (SignatureException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid JWT Signature");
        }
        catch (ExpiredJwtException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Expired JWT token");
        }
        catch (MalformedJwtException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid JWT token");
        }
        catch (UnsupportedJwtException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Unsupported JWT token");
        }
        catch (IllegalIdentifierException e){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"JWT claims string is empty");
        }
    }


}
