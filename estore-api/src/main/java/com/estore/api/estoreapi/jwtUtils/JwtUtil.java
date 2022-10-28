package com.estore.api.estoreapi.jwtUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "CaloriTeamE";

    Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), 
                            SignatureAlgorithm.HS256.getJcaName());

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
}
