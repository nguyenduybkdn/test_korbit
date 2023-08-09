package com.aptech.sem4eprojectbe.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.aptech.sem4eprojectbe.service.JwtUserDetailsService;

@Configuration
public class JwtSecurityConfig {
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http) throws Exception {
        return http.cors().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/authenticate", "/api/v1/contacts", "/api/v1/contact", "/api/v1/all-news",
                        "/api/v1/news", "/api/v1/products", "/api/v1/product", "/api/v1/categories", "/api/v1/category",
                        "/api/v1/feedbacks", "/api/v1/feedback", "/api/v1/all-about-us", "/api/v1/about-us",
                        "/api/v1/faqs", "/api/v1/faq", "/api/v1/feedbacks/by-product-id", "/api/v1/insert-user", "/api/v1/alcohol**","/api/v1/product-by-cateId", "/api/v1/search-by-name", "/api/v1/filter-combine")
                .permitAll()
                .requestMatchers("/api/v1/delete-feedback", "/api/v1/delete-invoice", "/api/v1/delete-invoice-items",
                        "/api/v1/delete-contact", "/api/v1/delete-about-us", "/api/v1/delete-category",
                        "/api/v1/delete-product", "/api/v1/update-product", "/api/v1/delete-user")
                .hasRole(JwtUserDetailsService.ADMIN)
                .anyRequest().authenticated().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
