package com.mini.mini_2.rest_area.domain;

import java.util.List;
import java.util.Optional;

import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.domain.enums.RestDirection;

public interface RestAreaRepository {
    RestAreaEntity save(RestAreaEntity e);
    Optional<RestAreaEntity> findById(Integer id);
    Optional<RestAreaEntity> findByCode(String code);
    List<RestAreaEntity> findAll();
    List<RestAreaEntity> findByDirection(RestDirection direction);
    void deleteById(Integer id);

    Optional<RestAreaEntity> findByAddress(String addr);

    //boolean existsById(Integer id);

}
