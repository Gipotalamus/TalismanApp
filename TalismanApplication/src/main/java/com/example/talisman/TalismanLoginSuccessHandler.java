package com.example.talisman;

import com.example.talisman.entities.TalismanUser;
import com.example.talisman.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by gipotalamus on 08.08.16.
 */
public class TalismanLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        TalismanUser talismanUser = userDetailsService.findOneByName(user.getUsername());
        talismanUser.setOnline(true);
        userDetailsService.saveWithoutEncodingPass(talismanUser);
        String URL = httpServletRequest.getContextPath() + "/";
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.sendRedirect(URL);

    }
}
