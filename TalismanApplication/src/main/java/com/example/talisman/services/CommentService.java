package com.example.talisman.services;

import com.example.talisman.entities.Comment;
import com.example.talisman.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by gipotalamus on 28.06.16.
 */
@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    public void save(Comment comment) {
        LocalDateTime date = LocalDateTime.now();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setUser(customUserDetailsService.findOneByName(userDetails.getUsername()));
        comment.setDate(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
        commentRepository.save(comment);
    }

    public void delete(Integer id) {
        commentRepository.delete(id);
    }

    public Comment get(Integer id) {
        return commentRepository.getOne(id);
    }

}
