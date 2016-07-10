package com.example.talisman.repositories;



import com.example.talisman.entities.TalismanMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by мир on 27.03.2016.
 */
public interface TalismanMemberRepository extends JpaRepository<TalismanMemberEntity, Integer> {
    TalismanMemberEntity findTopByOrderByIdDesc();
}
