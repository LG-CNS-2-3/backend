package com.mini.mini_2.facility.infrastructure.persistence.jpa;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.mini.mini_2.facility.domain.FacilityRepository;
import com.mini.mini_2.facility.domain.entity.FacilityEntity;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;

import lombok.RequiredArgsConstructor;

/**
 * 도메인 리포지토리(FacilityRepository)의 Spring Data JPA 구현체(어댑터)
 */
@Repository
@RequiredArgsConstructor
public class FacilityRepositoryAdapter implements FacilityRepository {

    private final SpringDataFacilityRepository jpa;

    @Override
    public FacilityEntity save(FacilityEntity e) {
        return jpa.save(e);
    }

    @Override
    public List<FacilityEntity> findByRestArea_RestAreaId(Integer restAreaId) {
        return jpa.findByRestArea_RestAreaId(restAreaId);
    }

    @Override
    public List<RestAreaEntity> findRestAreaByTypes(List<String> names) {
        return jpa.findRestAreaByTypes(names);
    }

    @Override
    public List<FacilityEntity> findAll() {
        return jpa.findAll();
    }
}