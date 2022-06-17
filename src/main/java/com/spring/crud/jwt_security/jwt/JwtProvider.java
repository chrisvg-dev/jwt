package com.spring.crud.jwt_security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.spring.crud.jwt_security.dto.JwtDto;
import com.spring.crud.jwt_security.entity.UserSecurity;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GENERA EL TOKEN Y VALIDA SI ESTÁ BIEN FORMADO
 */
@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private static final String ROLES = "roles";

    /**
     * LA INFORMACION DE LAS VARIABLES SIGUIENTES SALE DEL PROPERTIES
     */
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication authentication){
        UserSecurity mainUser = (UserSecurity) authentication.getPrincipal();

        /**
         * MODIFICACION PARA SOLO ENVIAR EL TOKEN
         */
        List<String> roles = mainUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return Jwts.builder()
                .setSubject(mainUser.getUsername())
                .claim(ROLES, roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token Mal formado");
        }  catch (UnsupportedJwtException e) {
            String message = String.format("Token no soportado %s", e.getMessage());
            logger.error(message);
        }  catch (ExpiredJwtException e) {
            logger.error("Token expirado");
        }catch (IllegalArgumentException e) {
            logger.error("Token vacio");
        } catch (SignatureException e) {
            logger.error("Fail en la forma");
        }

        return false;
    }

    /**
     * REFRESCAR TOKEN
     * @param jwtDto
     * @return
     */
    public String refreshToken(JwtDto jwtDto) throws ParseException {
        JWT jwt = JWTParser.parse(jwtDto.getToken()); // SE PARSEA EL TOKEN
        JWTClaimsSet claims = jwt.getJWTClaimsSet();
        String username = claims.getSubject();
        List<String> roles = (List<String>) claims.getClaim(ROLES);
        return Jwts.builder()
                .setSubject(username)
                .claim(ROLES, roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }
}
