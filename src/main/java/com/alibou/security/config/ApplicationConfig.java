package com.alibou.security.config;

import com.alibou.security.auditing.ApplicationAuditAware;
import com.alibou.security.dtos.UserDto;
import com.alibou.security.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  @Value("${authServerUrl}")
  private String authServerUrl;

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      return findUserByEmail(username);
    };
  }

  public User findUserByEmail(String email) {
    RestTemplate restTemplate = new RestTemplate();

    UserDto userDto = restTemplate
            .getForObject(authServerUrl + "/users/" + email, UserDto.class);
    User user = new User();
    user.setEmail(userDto.getEmail());
    user.setPassword(userDto.getPassword());
    user.setId(userDto.getId());
    user.setFirstname(userDto.getFirstname());
    user.setLastname(userDto.getLastname());
    return user;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  @Bean
  public AuditorAware<Integer> auditorAware() {
    return new ApplicationAuditAware();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
