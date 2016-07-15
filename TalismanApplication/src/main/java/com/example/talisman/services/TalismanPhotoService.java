package com.example.talisman.services;

import com.example.talisman.entities.TalismanPhotoEntity;
import com.example.talisman.repositories.TalismanPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gipotalamus on 14.07.16.
 */
@Service
public class TalismanPhotoService {

    @Autowired
    TalismanPhotoRepository photoRepository;

    public void addPhoto(TalismanPhotoEntity photo) {
        photoRepository.save(photo);
    }

    public int getMaxId() {
        TalismanPhotoEntity entity = photoRepository.findTopByOrderByIdDesc();
        return entity == null ? 1 : entity.getId();
    }

    public List<TalismanPhotoEntity> findAll() {
        return photoRepository.findAll();
    }

    public TalismanPhotoEntity find(int id) {
        return id==0 ? new TalismanPhotoEntity() : photoRepository.findOne(id);
    }
}
