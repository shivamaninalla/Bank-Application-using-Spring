// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.techlabs.app.security.JwtAuthenticationEntryPoint;
import com.techlabs.app.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {
   private JwtAuthenticationEntryPoint authenticationEntryPoint;
   private JwtAuthenticationFilter authenticationFilter;

   public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint, JwtAuthenticationFilter authenticationFilter) {
      this.authenticationEntryPoint = authenticationEntryPoint;
      this.authenticationFilter = authenticationFilter;
   }

   @Bean
   static PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
      return configuration.getAuthenticationManager();
   }

   @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http.csrf((csrf) -> {
         csrf.disable();
      }).authorizeHttpRequests((authorize) -> {
         ((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)((AuthorizeHttpRequestsConfigurer.AuthorizedUrl)authorize.requestMatchers(HttpMethod.POST, new String[]{"/api/students/**"})).hasRole("ADMIN").requestMatchers(new String[]{"/api/auth/**"})).permitAll().anyRequest()).authenticated();
      }).exceptionHandling((exception) -> {
         exception.authenticationEntryPoint(this.authenticationEntryPoint);
      }).sessionManagement((session) -> {
         session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
      });
      http.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
      return (SecurityFilterChain)http.build();
   }
}
