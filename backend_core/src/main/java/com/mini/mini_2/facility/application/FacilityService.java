package com.mini.mini_2.facility.application;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.mini.mini_2.exception.RestAreaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// [수정] DTO 임포트 경로 변경
import com.mini.mini_2.facility.application.dto.FacilityRequestDTO;
import com.mini.mini_2.facility.application.dto.FacilityResponseDTO;
// [수정] Entity 임포트 경로 변경
import com.mini.mini_2.facility.domain.entity.FacilityEntity;
// [핵심 수정] Infrastructure의 JPA 인터페이스가 아닌 Domain의 Repository 인터페이스를 임포트
import com.mini.mini_2.facility.domain.FacilityRepository;
import com.mini.mini_2.rest_area.application.dto.RestAreaResponseDTO;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;
import com.mini.mini_2.rest_area.domain.RestAreaRepository;

import jakarta.transaction.Transactional;

@Service
public class FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;
    
    @Autowired
    private RestAreaRepository restAreaRepository;
   

    // 편의시설 생성
    @Transactional
    public FacilityResponseDTO create(FacilityRequestDTO request) {
        System.out.println("[FacilityService] create : "+ request);

        RestAreaEntity restArea = restAreaRepository.findById(request.getRestAreaId()).orElseThrow(
                () -> new RestAreaNotFoundException(request.getRestAreaId())
        );

        FacilityEntity facility = request.toEntity(restArea);
        return FacilityResponseDTO.fromEntity(facilityRepository.save(facility));
    }
    
    // 휴게소 ID 기반 편의시설 조회
    public List<FacilityResponseDTO> findByRestAreaId(Integer restAreaId) {
        
        List<FacilityEntity> entities = facilityRepository.findByRestArea_RestAreaId(restAreaId);
        
        return entities.stream()
                         .map(entity -> FacilityResponseDTO.fromEntity(entity))
                         .toList();
        
    }

    // 원하는 편의시설이 있는 휴게소 조회
    public List<RestAreaResponseDTO> searchByNames(List<String> names) {

        List<String> cleandNames = (names == null ? List.<String>of() : names)
                .stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .distinct()
                .toList() ;
        
        if (cleandNames.isEmpty()) return List.of() ;        
        return facilityRepository.findRestAreaByTypes(cleandNames)
                .stream()
                .map(RestAreaResponseDTO::fromEntity)
                .toList() ;
    }
}
