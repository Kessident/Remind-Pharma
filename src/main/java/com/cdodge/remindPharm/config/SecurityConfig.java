package com.cdodge.remindPharm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
//        http.authorizeRequests()
//            .antMatchers("/", "/login", "register").permitAll()
//            .antMatchers("/user/**").hasRole("USER")
//            .and()
//            .formLogin()
//            .loginPage("/login").failureUrl("/login-error");
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource)
//            .usersByUsernameQuery("select username, password from users where username=?");
//            //.inMemoryAuthentication()
//            //.withUser("user").password("password").roles("USER");
//    }
}
