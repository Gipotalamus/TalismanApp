package com.example.talisman;

import com.example.talisman.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.inject.Inject;

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
                .formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?err").permitAll()
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
