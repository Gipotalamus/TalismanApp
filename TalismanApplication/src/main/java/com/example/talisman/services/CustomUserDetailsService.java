package com.example.talisman.services;

import com.example.talisman.entities.TalismanUser;
import com.example.talisman.repositories.TalismanUserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by мир on 14.04.2016.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Inject
    TalismanUserRepository talismanUserRepository;

    @Inject
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        TalismanUser user = findOneByName(s);
        return new User(user.getName(), user.getPassword(), getAuthorities(user.getRole()));
    }

    public TalismanUser findOneByName(String name) {
        return talismanUserRepository.findOneByName(name);
    }


    public List<GrantedAuthority> getAuthorities(String role) {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();

            authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    public void save(TalismanUser user) {
        String pass = user.getPassword();
        String encodedPass = passwordEncoder.encode(pass);
        user.setPassword(encodedPass);
        talismanUserRepository.save(user);
    }
}
