package com.example.talisman.repositories;

import com.example.talisman.entities.Comment;
import com.example.talisman.entities.TalismanEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by gipotalamus on 28.06.16.
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
