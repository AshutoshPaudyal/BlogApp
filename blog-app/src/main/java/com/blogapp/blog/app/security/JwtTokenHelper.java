package com.blogapp.blog.app.security;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    private final String secret = "3778217A25432A462D4A614E645267556B58703273357638792F423F4428472B";

    //retrieve user from token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token,Claims::getSubject);
    }

    //retrieve expiration date from token
    private Date getExpirationFromToken(String token) {

        return getClaimFromToken(token,Claims::getExpiration);
    }

    //retrieve claim from token
    public <T> T getClaimFromToken(String token, Function<Claims,T> claimResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    //for retrieving any information from token we need secret key
    public Claims getAllClaimsFromToken(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .parseClaimsJws(token)
                .getBody();
    }

    //check if token is expired
    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(
            UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    //generate token for user

    //while creating token
    //1.Define claims of the token,like Issue, Expiration, Subject, and the ID
    //2.Sign the JWT using HS256 Algorithm and secret key
    //3. According to JWS Compact Serialization
    //compaction of the JWT to a URL-safe string
    public String generateToken(
            Map<String, Object> claims, UserDetails userDetails){
        return Jwts.
                builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000 * 60 *24)) //1000 millisecond + 24 hrs
                .signWith(getSignInKey(),SignatureAlgorithm.HS256)
                .compact();
    }

    //validateToken
    public boolean validateToken(String token,UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
