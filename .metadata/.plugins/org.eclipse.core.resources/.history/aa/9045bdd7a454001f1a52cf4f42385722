// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
   private JwtTokenProvider jwtTokenProvider;
   private UserDetailsService userDetailsService;

   public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
      this.jwtTokenProvider = jwtTokenProvider;
      this.userDetailsService = userDetailsService;
   }

   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
      String token = this.getTokenFromRequest(request);
      if (StringUtils.hasText(token) && this.jwtTokenProvider.validateToken(token)) {
         String username = this.jwtTokenProvider.getUsername(token);
         UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
         UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, (Object)null, userDetails.getAuthorities());
         authenticationToken.setDetails((new WebAuthenticationDetailsSource()).buildDetails(request));
         SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }

      filterChain.doFilter(request, response);
   }

   private String getTokenFromRequest(HttpServletRequest request) {
      String bearerToken = request.getHeader("Authorization");
      return StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ") ? bearerToken.substring(7, bearerToken.length()) : null;
   }
}
