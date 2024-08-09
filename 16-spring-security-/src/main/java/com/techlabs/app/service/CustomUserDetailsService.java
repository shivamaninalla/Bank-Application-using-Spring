// Source code is decompiled from a .class file using FernFlower decompiler.
package com.techlabs.app.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techlabs.app.entity.User;
import com.techlabs.app.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
   private UserRepository userRepository;

   public CustomUserDetailsService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
      User user = (User)this.userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> {
         return new UsernameNotFoundException("User not found with username or email: " + usernameOrEmail);
      });
      Set<GrantedAuthority> authorities = (Set)user.getRoles().stream().map((role) -> {
         return new SimpleGrantedAuthority(role.getName());
      }).collect(Collectors.toSet());
      return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
   }
}
