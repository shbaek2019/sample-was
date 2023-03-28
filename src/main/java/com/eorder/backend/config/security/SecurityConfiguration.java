package com.eorder.backend.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //
    private JwtTokenProvider jwtTokenProvider;

    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        //
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //
        httpSecurity.httpBasic().disable()
            .csrf().disable()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            //.antMatchers("/sign-api/sign-in", "/sign-api/sign-up", "/sign-api/exception").permitAll()
            .antMatchers("/sign-api/sign-in", "/sign-api/sign-up", "/sign-api/exception").permitAll()
            .antMatchers(HttpMethod.GET, "/product/**").permitAll()
            
            .antMatchers("**exception**").permitAll()
            
            .anyRequest().authenticated()
            
            .and()
            .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
            .and()
            .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        //
        webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception");
    }
}
