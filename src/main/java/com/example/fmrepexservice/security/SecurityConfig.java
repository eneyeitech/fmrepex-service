package com.example.fmrepexservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoderConfig b;

    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    // Acquiring the builder
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userDetailsService) // user store 1
                .passwordEncoder(b.getEncoder());
        auth
                .inMemoryAuthentication() // user store 2
                .withUser("Admin").password("hardcoded").roles("MANAGER")
                .and().passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    // creating a PasswordEncoder that is needed in two places


    /** @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
    .mvcMatchers(HttpMethod.POST,"/api/auth/signup").permitAll()
    .mvcMatchers("/api/empl/payment").authenticated()
    .and().httpBasic()
    .and()
    .csrf().disable();
    }*/

    public void configure(HttpSecurity http) throws Exception {
        http.cors();
        http.httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint) // Handle auth error
                .and()
                .csrf().disable().headers().frameOptions().disable() // for Postman, the H2 console
                .and()
                .authorizeRequests() // manage access
                .mvcMatchers(HttpMethod.POST, "/api/manager/**").hasAnyRole("MANAGER")
                .mvcMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("MANAGER")
                .mvcMatchers("/api/admin/**").hasAnyRole("ADMINISTRATOR")
                .mvcMatchers(HttpMethod.POST, "/api/tenant/**").hasAnyRole("TENANT")
                .mvcMatchers("/api/dependant/**").hasAnyRole("DEPENDANT")
                //.mvcMatchers(HttpMethod.POST, "/api/manager/new/technician").hasAnyRole("MANAGER")
                .mvcMatchers(HttpMethod.GET, "/api/manager/users").hasAnyRole("MANAGER")
                .mvcMatchers(HttpMethod.GET, "/api/manager/announcement").hasAnyRole("MANAGER")
                .mvcMatchers(HttpMethod.GET, "/api/tenant/announcement").hasAnyRole("TENANT")
                .mvcMatchers("/**").permitAll()

                /**.mvcMatchers(HttpMethod.POST, "/api/auth/signup").permitAll()
                .mvcMatchers(HttpMethod.POST, "/api/user/new").hasAnyRole("MANAGER")
                .mvcMatchers(HttpMethod.POST, "/api/building/new").hasAnyRole("MANAGER")
                .mvcMatchers(HttpMethod.POST, "/api/company/new").hasAnyRole("MANAGER")
                .mvcMatchers(HttpMethod.POST, "api/building/{id}/maintenance").hasAnyRole("TENANT")
                .mvcMatchers(HttpMethod.PUT, "/api/tenant/{email}/building/{bid}").hasAnyRole("MANAGER")
                .mvcMatchers(HttpMethod.POST, "/api/tenant/{email}/building/{bid}").hasAnyRole("MANAGER")
                .mvcMatchers(HttpMethod.GET, "/api/empl/payment").hasAnyRole("ACCOUNTANT", "USER")*/

                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                // other matchers
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // no session

    }
}

