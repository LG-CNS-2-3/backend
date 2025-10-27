package com.mini.mini_2.all;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import com.mini.mini_2.rest_area.domain.RestAreaRepository; // ✅ 포트
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity; // ✅ 엔티티
import com.mini.mini_2.rest_area.infrastructure.persistence.jpa.RestAreaRepositoryAdapter; // ✅ 어댑터
// SpringDataRestAreaRepository는 @EnableJpaRepositories 로 자동 등록됨

@DataJpaTest(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.show-sql=true",
    "spring.jpa.properties.hibernate.format_sql=true"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) // ✅ H2 강제
@ActiveProfiles("test")

// ✅ **정확한 패키지**로 엔티티/레포 스캔
@EntityScan(basePackages = "com.mini.mini_2.rest_area.domain.entity")
@EnableJpaRepositories(basePackages = "com.mini.mini_2.rest_area.infrastructure.persistence.jpa")

// ✅ 포트 구현(어댑터)만 테스트 컨텍스트에 주입
@Import(RestAreaRepositoryAdapter.class)
class RestAreaIntegrationTest {

    // 포트 타입으로 주입받지만, 내부적으로 SpringData JPA가 동작
    @Autowired
    RestAreaRepository restAreaRepository;

    @Test
    void dbCrud_deleteById_Integer_Works() {
        // ===== CREATE =====
        RestAreaEntity entity = RestAreaEntity.builder()
                .name("고양휴게소")
                .direction("상행")
                .code("0001")
                .tel("031-000-0000")
                .address("경기도 고양시")
                .routeName("문산고속도로")
                .build();

        RestAreaEntity saved = restAreaRepository.save(entity);
        Integer id = saved.getRestAreaId();
        assertNotNull(id);

        // ===== READ =====
        Optional<RestAreaEntity> found = restAreaRepository.findById(id);
        assertTrue(found.isPresent());
        assertEquals("고양휴게소", found.get().getName());
        assertEquals("상행", found.get().getDirection());
        assertEquals("0001", found.get().getCode());

        // ===== UPDATE =====
        RestAreaEntity toUpdate = found.get();
        toUpdate.setTel("031-1234-5678");
        RestAreaEntity updated = restAreaRepository.save(toUpdate);
        assertEquals("031-1234-5678", updated.getTel());

        // ===== DELETE (Integer) =====
        restAreaRepository.deleteById(id);

        // ===== VERIFY =====
        assertTrue(restAreaRepository.findById(id).isEmpty());
    }
}
