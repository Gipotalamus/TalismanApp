package com.example.talisman.services;

import com.example.talisman.entities.TalismanUser;
import com.example.talisman.genconfirm.ConfirmCodeGenerator;
import com.example.talisman.repositories.TalismanUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by мир on 14.04.2016.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    TalismanUserRepository talismanUserRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    SimpleMailMessage mailMessage;

    @Autowired
    ConfirmCodeGenerator codeGenerator;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        TalismanUser user = findOneByName(s);
        return new User(user.getName(), user.getPassword(), getAuthorities(user.getRole()));
    }

    public TalismanUser findOneByName(String name) {
        return talismanUserRepository.findOneByNameAndConfirm(name, "confirmed");
    }


    public List<GrantedAuthority> getAuthorities(String role) {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();

            authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    public void save(TalismanUser user) {
        String pass = user.getPassword();
        LocalDateTime date = LocalDateTime.now();
        user.setVisit(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
        String encodedPass = passwordEncoder.encode(pass);
        user.setPassword(encodedPass);
        talismanUserRepository.save(user);
    }

    public void saveWithoutEncodingPass(TalismanUser user){
        talismanUserRepository.save(user);
    }

    public List<TalismanUser> getAll() {
        return talismanUserRepository.findAll();
    }

    public void setUsersOffline() {
        List<TalismanUser> onlineList = talismanUserRepository.findByOnline(true);
        onlineList.forEach(talismanUser -> talismanUser.setOnline(false));
        talismanUserRepository.save(onlineList);
    }

    public void remove(String name) {
        talismanUserRepository.deleteByName(name);
    }

    public void checkCode(String code) {
        TalismanUser user = talismanUserRepository.findOneByConfirm(code);
        if (user != null) {
            user.setConfirm("confirmed");
            talismanUserRepository.save(user);
        }
    }

    public void addUser(TalismanUser talismanUser, String path) {
        mailMessage.setTo(talismanUser.getEmail());
        String code = codeGenerator.generateCode(talismanUser.getName());
        String confirmURL = path + "/confirm?code=" + code;
        mailMessage.setText("Please, confirm your registration! Follow link below: " + confirmURL);
        try {
            mailSender.send(mailMessage);
        } catch (MailException e) {
            e.printStackTrace();
        }
        talismanUser.setRole("ROLE_USER");
        talismanUser.setConfirm(code);
        save(talismanUser);
    }
}
