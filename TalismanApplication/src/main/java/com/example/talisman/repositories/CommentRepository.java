package com.example.talisman.repositories;

import com.example.talisman.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gipotalamus on 28.06.16.
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
