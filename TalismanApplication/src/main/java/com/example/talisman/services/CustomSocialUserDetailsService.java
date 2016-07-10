package com.example.talisman.services;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by мир on 22.04.2016.
 */
@Service
public class CustomSocialUserDetailsService implements SocialUserDetailsService {

    @Inject
    CustomUserDetailsService userDetailsService;

    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException {
        return (SocialUserDetails) userDetailsService.loadUserByUsername(s);
    }
}
