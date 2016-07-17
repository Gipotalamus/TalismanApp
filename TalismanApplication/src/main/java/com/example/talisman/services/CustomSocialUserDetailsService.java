package com.example.talisman.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by мир on 22.04.2016.
 */
@Service
public class CustomSocialUserDetailsService implements SocialUserDetailsService {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException {
        return (SocialUserDetails) userDetailsService.loadUserByUsername(s);
    }
}
