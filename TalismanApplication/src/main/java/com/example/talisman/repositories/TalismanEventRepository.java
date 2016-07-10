package com.example.talisman.repositories;

import com.example.talisman.entities.TalismanEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


/**
 * Created by мир on 25.03.2016.
 */
@RepositoryRestResource(path = "events")
public interface TalismanEventRepository extends JpaRepository<TalismanEventEntity, Integer> {


}
