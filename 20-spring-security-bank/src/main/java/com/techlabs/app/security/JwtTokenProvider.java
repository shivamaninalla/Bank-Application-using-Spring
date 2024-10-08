// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.security;

import com.techlabs.app.exception.BankApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
   @Value("${app.jwt-secret}")
   private String jwtSecret;
   @Value("${app-jwt-expiration-milliseconds}")
   private long jwtExpirationDate;

   public JwtTokenProvider() {
   }

   public String generateToken(Authentication authentication) {
      String username = authentication.getName();
      Date currentDate = new Date();
      Date expireDate = new Date(currentDate.getTime() + this.jwtExpirationDate);
      String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expireDate).signWith(this.key()).compact();
      return token;
   }

   private Key key() {
      return Keys.hmacShaKeyFor((byte[])Decoders.BASE64.decode(this.jwtSecret));
   }

   public String getUsername(String token) {
      Claims claims = (Claims)Jwts.parserBuilder().setSigningKey(this.key()).build().parseClaimsJws(token).getBody();
      String username = claims.getSubject();
      return username;
   }

   public boolean validateToken(String token) {
      try {
         Jwts.parserBuilder().setSigningKey(this.key()).build().parse(token);
         return true;
      } catch (MalformedJwtException var3) {
         throw new BankApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
      } catch (ExpiredJwtException var4) {
         throw new BankApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
      } catch (UnsupportedJwtException var5) {
         throw new BankApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
      } catch (IllegalArgumentException var6) {
         throw new BankApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
      }
   }
}
