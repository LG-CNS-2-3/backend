package com.mini.mini_2.facility.domain;

import java.util.List;

import com.mini.mini_2.facility.domain.entity.FacilityEntity;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;

/**
 * Facility 도메인 리포지토리 인터페이스 (Application 계층이 의존하는 포트)
 */
public interface FacilityRepository {

    FacilityEntity save(FacilityEntity e);

    List<FacilityEntity> findByRestArea_RestAreaId(Integer restAreaId);

    List<RestAreaEntity> findRestAreaByTypes(List<String> names);

    List<FacilityEntity> findAll();
}