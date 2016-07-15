package com.example.talisman.services;


import com.example.talisman.entities.TalismanMemberEntity;
import com.example.talisman.repositories.TalismanMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by мир on 27.03.2016.
 */
@Service
public class TalismanMemberService {

    @Inject
    TalismanMemberRepository talismanMemberRepository;

    public List<TalismanMemberEntity> getAll() {
        return talismanMemberRepository.findAll();
    }

    public void add(TalismanMemberEntity member) {
        talismanMemberRepository.save(member);
    }

    public int getMaxId() {
        TalismanMemberEntity entity = talismanMemberRepository.findTopByOrderByIdDesc();
        return entity == null ? 1 : entity.getId();
    }

    public TalismanMemberEntity find(Integer id) {
        return talismanMemberRepository.findOne(id);
    }

    public void delete(Integer id) {
        talismanMemberRepository.delete(id);
    }
}
