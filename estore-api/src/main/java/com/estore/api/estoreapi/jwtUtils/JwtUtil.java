package com.estore.api.estoreapi.jwtUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 * The JwtUtil class
 * A utilities class that has methods that are all related to JWT
 * 
 * @author Team-E
 */
@Component
public class JwtUtil {
    private static final String SECRET_KEY = "PIGSd1uVlo28HGCaXUS1wcCNWQC83XW81LB64Ty2YPgNZxXj2N";

    private static final int TOKEN_VALIDITY = 3600 * 5;

    Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), 
                            SignatureAlgorithm.HS256.getJcaName());
    /**
     * Create a Role with the given roleId, and name
     * @param token The id of the role
     * 
     * @return a number representing this roles id
     */
    public String getUserNameFromJWT(String token) {
        return getClaimFromJWT(token, Claims::getSubject);
    }
    private <T> T getClaimFromJWT(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(token).getBody();
    }
    //return true if token is validated, else false
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromJWT(token);
        return ( username.equals(userDetails.getUsername()) && !isTokenExpired(token) );
    }

    private boolean isTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimFromJWT(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims)
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+ TOKEN_VALIDITY * 1000))
        .signWith(hmacKey).compact();
    }
}
