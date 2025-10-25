package com.mini.mini_2.rest_area.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mini.mini_2.rest_area.domain.RestAreaRepository;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RestAreaRepositoryAdapter implements RestAreaRepository {

    private final SpringDataRestAreaRepository jpa;

    @Override public RestAreaEntity save(RestAreaEntity e) { return jpa.save(e); }
    @Override public Optional<RestAreaEntity> findById(Integer id) { return jpa.findById(id); }
    @Override public Optional<RestAreaEntity> findByCode(String code) { return jpa.findByCode(code); }
    @Override public List<RestAreaEntity> findAll() { return jpa.findAll(); }
    @Override public List<RestAreaEntity> findByDirection(String direction) { return jpa.findByDirection(direction); }
    @Override public void deleteById(Integer id) { jpa.deleteById(id); }

    @Override public Optional<RestAreaEntity> findByAddress(String addr) {return jpa.findByAddress(addr);}

    //@Override public boolean existsById(Integer id) { return jpa.existsById(id); }//

    
}
