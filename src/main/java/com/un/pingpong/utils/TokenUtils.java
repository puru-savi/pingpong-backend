package com.un.pingpong.utils;


import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import com.un.pingpong.exception.PingPongException;
import io.jsonwebtoken.*;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;

public class TokenUtils {

    private static String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";

    public static String createJWT(long id, String userName) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().setId(String.valueOf(id))
                .claim("userName", userName)
                .setIssuedAt(now)
                .setId(String.valueOf(id))
                .setSubject("User")
                .setIssuer(userName)
                .signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }

    public static Claims parseJWT(String jwt) throws PingPongException {

        Claims claims;
        try
        {
             claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                    .parseClaimsJws(jwt).getBody();
            System.out.println("ID: " + claims.getId());
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Issuer: " + claims.getIssuer());
        }catch (Exception es)
        {
            throw new PingPongException(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }

        return claims;
    }
}
