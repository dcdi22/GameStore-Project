package com.company.GameStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username = ?")
                .passwordEncoder(encoder);

    }


    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.PUT, "/console/{consoleId}").hasAuthority("ROLE_STAFF")
                .mvcMatchers(HttpMethod.PUT, "/game/{gameId}").hasAuthority("ROLE_STAFF")
                .mvcMatchers(HttpMethod.PUT, "/tshirt/{tshirtId}").hasAuthority("ROLE_STAFF")
                .mvcMatchers(HttpMethod.POST, "/console").hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.POST, "/game").hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.POST, "/tshirt").hasAuthority("ROLE_MANAGER")
                .mvcMatchers(HttpMethod.DELETE, "/console/{consoleId}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/game/{gameId}").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE, "/tshirt/{tshirtId}").hasAuthority("ROLE_ADMIN");


        httpSecurity
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

    }

}
