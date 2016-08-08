package com.example.talisman;

import com.example.talisman.entities.TalismanUser;
import com.example.talisman.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by gipotalamus on 09.08.16.
 */
public class SessionListener implements ApplicationListener<SessionDestroyedEvent> {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event)
    {
        for (SecurityContext securityContext : event.getSecurityContexts())
        {
            Authentication authentication = securityContext.getAuthentication();
            User user = (User) authentication.getPrincipal();
            TalismanUser talismanUser = userDetailsService.findOneByName(user.getUsername());
            LocalDateTime date = LocalDateTime.now();
            talismanUser.setVisit(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
            talismanUser.setOnline(false);
            userDetailsService.saveWithoutEncodingPass(talismanUser);
            System.out.println("saved");
        }
    }

}