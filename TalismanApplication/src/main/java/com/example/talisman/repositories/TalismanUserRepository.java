package com.example.talisman.repositories;

import com.example.talisman.entities.TalismanUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by мир on 26.04.2016.
 */
@RepositoryRestResource(exported = false)
public interface TalismanUserRepository extends JpaRepository<TalismanUser, Integer> {

    TalismanUser findOneByNameAndConfirm(String name, String confirm);

    List<TalismanUser> findByOnline(boolean online);

    @Transactional
    void deleteByName(String name);

    TalismanUser findOneByConfirm(String confirm);
}
