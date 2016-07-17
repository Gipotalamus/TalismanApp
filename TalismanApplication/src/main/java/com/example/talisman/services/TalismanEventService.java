package com.example.talisman.services;


import com.example.talisman.entities.TalismanEventEntity;
import com.example.talisman.entities.TalismanPhotoEntity;
import com.example.talisman.repositories.TalismanEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * Created by мир on 25.03.2016.
 */
@Service
public class TalismanEventService {

    @Autowired
    private TalismanPhotoService photoService;

    @Autowired
    private TalismanEventRepository talismanEventRepository;


    public TalismanEventEntity saveOrUpdate(TalismanEventEntity talismanEventEntity) {
        LocalDateTime date = LocalDateTime.now();
        talismanEventEntity.setDate(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
        return talismanEventRepository.save(talismanEventEntity);
    }
    public TalismanEventEntity get(int id){
        if (id == 0) return new TalismanEventEntity();
        return talismanEventRepository.findOne(id);
    }

    public void delete(int id) {
        TalismanEventEntity event = get(id);
        List<TalismanPhotoEntity> photos = event.getPhotos();
        photoService.delete(photos);
        talismanEventRepository.delete(id);
    }

    public Page<TalismanEventEntity> getPaginatedEvents(Pageable pageable) {
        return getAll(pageable);
    }

    public List<TalismanEventEntity> getAll() {
        return talismanEventRepository.findAll();
    }

    public Page<TalismanEventEntity> getAll(Pageable pageable){
        return talismanEventRepository.findAll(pageable);
    }

    public void increaseViews(int id) {
        TalismanEventEntity event = get(id);
        event.setViews(event.getViews() + 1);
        talismanEventRepository.save(event);
    }

}
