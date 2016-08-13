package com.example.talisman.services;

import com.example.talisman.entities.TalismanEntity;
import com.example.talisman.repositories.TalismanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gipotalamus on 29.07.16.
 */
@Service
public class TalismanService {
    @Autowired
    TalismanRepository talismanRepository;

    public TalismanEntity get() {
        return talismanRepository.findOne(1);
    }

    public void saveOrUpdate(TalismanEntity talismanEntity) {
        talismanRepository.save(talismanEntity);
    }
}
