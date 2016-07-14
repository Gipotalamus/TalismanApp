package com.example.talisman.repositories;

import com.example.talisman.entities.TalismanPhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gipotalamus on 14.07.16.
 */
public interface TalismanPhotoRepository extends JpaRepository<TalismanPhotoEntity, Integer> {
    TalismanPhotoEntity findTopByOrderByIdDesc();
}
