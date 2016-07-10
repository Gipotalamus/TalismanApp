package com.example.talisman.entities.projections;

import com.example.talisman.entities.TalismanEventEntity;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created by gipotalamus on 24.05.16.
 */
@Projection(name = "eventProjection", types = TalismanEventEntity.class)
public interface EventProjection {
    String getText();
}
