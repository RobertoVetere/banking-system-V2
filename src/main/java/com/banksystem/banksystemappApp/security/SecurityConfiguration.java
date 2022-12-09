package com.banksystem.banksystemappApp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalAuthentication
public class SecurityConfiguration {

    //

    @Bean
    PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConf) throws Exception {
        return authConf.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeHttpRequests().anyRequest().permitAll();
        /*
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/user-area").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/user-admin-area**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/user-details").hasAnyRole("ADMIN", "USER")


         */
        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }

}
