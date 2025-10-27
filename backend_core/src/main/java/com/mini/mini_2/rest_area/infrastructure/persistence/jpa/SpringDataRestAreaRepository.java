package com.mini.mini_2.rest_area.infrastructure.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;

public interface SpringDataRestAreaRepository extends JpaRepository<RestAreaEntity, Integer> {
    List<RestAreaEntity> findByDirection(String direction);
    Optional<RestAreaEntity> findByCode(String code);

    Optional<RestAreaEntity> findByAddress(String addr);
}
