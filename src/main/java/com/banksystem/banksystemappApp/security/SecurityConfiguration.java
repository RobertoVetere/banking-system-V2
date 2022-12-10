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

       //httpSecurity.authorizeHttpRequests().anyRequest().permitAll();
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/account-holder/balance/{id}").hasRole("ACCOUNTHOLDER")
                .requestMatchers(HttpMethod.GET, "/account-holder/transfer").hasRole("ACCOUNTHOLDER")
                .requestMatchers(HttpMethod.GET, "/account-holder/savings-balance/{id}").hasRole("ACCOUNTHOLDER")
                .requestMatchers(HttpMethod.GET, "/account-holder/deposit/{id}").hasRole("ACCOUNTHOLDER")
                .requestMatchers(HttpMethod.GET, "/account-holder/withdrawal/{id}").hasRole("ACCOUNTHOLDER")
                .requestMatchers(HttpMethod.GET, "/account-holder/credit-card/{id}").hasRole("ACCOUNTHOLDER")
                .requestMatchers(HttpMethod.GET, "/account-holder/all").hasRole("ACCOUNTHOLDER")
                .requestMatchers(HttpMethod.GET, "/admin/checking-all").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/admin/create-profile").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/admin/add-checking").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/admin/add-saving").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/admin/add-credit-card").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/admin/update-balance/{id}").hasRole("ADMIN")


                ////////////////////////////

              //  .requestMatchers(HttpMethod.GET, "/account-holder/transfer").hasRole("AccountHolder")
                 //.requestMatchers(HttpMethod.GET, "/user-admin-area**").hasRole("ADMIN")
              //  .requestMatchers(HttpMethod.GET, "/user-details").hasAnyRole("ADMIN", "USER")
                .anyRequest().permitAll();

        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }

}
