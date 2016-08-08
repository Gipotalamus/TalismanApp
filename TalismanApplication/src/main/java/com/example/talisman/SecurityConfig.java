package com.example.talisman;

import com.example.talisman.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by мир on 14.04.2016.
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Inject
    CustomUserDetailsService userDetailsService;

    @Bean
    BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationSuccessHandler talismanSuccessHandler() { return new TalismanLoginSuccessHandler();}

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers("/*/", "/members/{\\\\d+}").permitAll()
                .antMatchers("/talismanEvents/**", "/members/**", "/comments/**", "/photos/**").hasRole("ADMIN")
                .antMatchers("comments/add").hasAnyRole()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?err").successHandler(talismanSuccessHandler()).permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll()
                .and()
                .csrf().disable()
                .rememberMe().key("talisman").rememberMeCookieName("tali-coockie").rememberMeParameter("remember-me").tokenValiditySeconds(31536000)
                .and()
                .apply(new SpringSocialConfigurer()
                        .postLoginUrl("/")
                        .alwaysUsePostLoginUrl(true));

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}
