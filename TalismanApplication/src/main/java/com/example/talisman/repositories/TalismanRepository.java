package com.example.talisman.repositories;

import com.example.talisman.entities.TalismanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gipotalamus on 29.07.16.
 */
public interface TalismanRepository extends JpaRepository<TalismanEntity, Integer> {
}
