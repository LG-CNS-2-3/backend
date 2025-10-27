package com.mini.mini_2.facility.infrastructure.persistence.jpa; // [수정] 패키지 변경

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository; // [수정] @Repository는 Adapter로 이동

// [수정] Entity 임포트 경로 변경
import com.mini.mini_2.facility.domain.entity.FacilityEntity;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;

// [수정] 기존 FacilityRepository -> SpringDataFacilityRepository로 이름 변경
public interface SpringDataFacilityRepository extends JpaRepository<FacilityEntity, Integer> {

    // (내부 로직은 기존과 동일)

    List<FacilityEntity> findByRestArea_RestAreaId(Integer restAreaId);

    @Query("""
           SELECT f.restArea 
           FROM   FacilityEntity f  
           WHERE  f.name IN :names
           GROUP BY f.restArea
           HAVING COUNT(DISTINCT f.name) = :#{#names.size()}
           """)
    List<RestAreaEntity> findRestAreaByTypes(@Param("names") List<String> names);
}