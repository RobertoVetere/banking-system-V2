package com.banksystem.banksystemappapp.security;

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

                .requestMatchers(HttpMethod.GET, "/account-holder/balance/{id}").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/account-holder/transfer").hasRole("USER")
                .requestMatchers(HttpMethod.POST, "/account-holder/savings-balance/{id}").hasRole("USER")
                .requestMatchers(HttpMethod.PATCH, "/account-holder/deposit/{id}").hasRole("USER")
                .requestMatchers(HttpMethod.PATCH, "/account-holder/withdrawal/{id}").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/account-holder/credit-card/{id}").hasRole("USER")
                .requestMatchers(HttpMethod.GET, "/account-holder/all").hasRole("USER")

                .requestMatchers(HttpMethod.GET, "/admin/checking-all/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/create-account-holder/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/admin/add-checking/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/admin/add-saving/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/admin/add-credit-card/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/admin/update-balance/{id}").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/admin/add-third-party/").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/admin/add-third-party/").hasRole("ADMIN")

                .requestMatchers(HttpMethod.PATCH, "/third-party/payment").hasRole("THIRDPARTY")
                .requestMatchers(HttpMethod.PATCH, "/third-party/receipts").hasRole("THIRDPARTY")



                ////////////////////////////

              //  .requestMatchers(HttpMethod.GET, "/account-holder/transfer").hasRole("AccountHolder")
                 //.requestMatchers(HttpMethod.GET, "/user-admin-area**").hasRole("ADMIN")
              //  .requestMatchers(HttpMethod.GET, "/user-details").hasAnyRole("ADMIN", "USER")

                .anyRequest().permitAll();

        httpSecurity.csrf().disable();

        return httpSecurity.build();
    }

}
