package com.example.talisman.repositories;

import com.example.talisman.entities.TalismanUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by мир on 26.04.2016.
 */
@RepositoryRestResource(exported = false)
public interface TalismanUserRepository extends JpaRepository<TalismanUser, Integer> {
    TalismanUser findOneByName(String name);
}
