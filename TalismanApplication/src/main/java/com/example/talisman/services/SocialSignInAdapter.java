package com.example.talisman.services;

import com.example.talisman.entities.TalismanUser;
import com.example.talisman.repositories.TalismanUserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by мир on 24.04.2016.
 */
@Service
public class SocialSignInAdapter implements SignInAdapter {

    @Inject
    private TalismanUserRepository talismanUserRepository;

    @Inject
    private Facebook facebook;



    @Override
    public String signIn(String s, Connection<?> connection, NativeWebRequest nativeWebRequest) {
        String name = facebook.userOperations().getUserProfile().getName();
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(name, null, getAuthorities("ROLE_USER"));
        SecurityContextHolder.getContext().setAuthentication(result);

        return null;
    }

    public List<GrantedAuthority> getAuthorities(String role) {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
