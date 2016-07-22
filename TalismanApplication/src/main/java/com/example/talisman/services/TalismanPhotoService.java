package com.example.talisman.services;

import com.example.talisman.entities.TalismanPhotoEntity;
import com.example.talisman.repositories.TalismanPhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by gipotalamus on 14.07.16.
 */
@Service
public class TalismanPhotoService {

    @Autowired
    TalismanPhotoRepository photoRepository;

    public void addPhoto(TalismanPhotoEntity photo) {
        LocalDateTime date = LocalDateTime.now();
        photo.setDate(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
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
        return id == 0 ? new TalismanPhotoEntity() : photoRepository.findOne(id);
    }

    public void delete(int id) {
        String filePath = "/home/gipotalamus/Idea/" + find(id).getPhoto();
        System.out.println(filePath);
        File file = new File(filePath);
        file.delete();
        photoRepository.delete(id);
    }

    public void delete(List<TalismanPhotoEntity> photos) {
        for (TalismanPhotoEntity photoEntity: photos){
            delete(photoEntity.getId());
        }

    }

    public Page<TalismanPhotoEntity> findAll(Pageable pageable) {
        return photoRepository.findAll(pageable);
    }
}
