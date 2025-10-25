package com.mini.mini_2.all;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mini.mini_2.rest_area.domain.RestAreaRepository;
import com.mini.mini_2.rest_area.domain.entity.RestAreaEntity;

@ExtendWith(MockitoExtension.class) // ✅ 스프링 미사용, 컨텍스트 X
class RestAreaTest {

    @Mock
    RestAreaRepository restAreaRepository;

    @Test
    void crud() {
        var entity = RestAreaEntity.builder()
                .name("고양휴게소")
                .direction("상행")
                .code("0001")
                .tel("031-000-0000")
                .address("경기도 고양시 덕양구")
                .routeName("문산고속도로")
                .build();

        when(restAreaRepository.findByCode("0001"))
                .thenReturn(Optional.of(entity));

        var found = restAreaRepository.findByCode("0001");
        assertTrue(found.isPresent());
        assertEquals("고양휴게소", found.get().getName());
    }

    @Test
    void filterTest() {
        var list = List.of(
            RestAreaEntity.builder().name("시흥").direction("상행").build(),
            RestAreaEntity.builder().name("안성").direction("하행").build()
        );
        when(restAreaRepository.findAll()).thenReturn(list);

        var all = restAreaRepository.findAll();
        var up = all.stream().filter(e -> "상행".equals(e.getDirection())).toList();
        var down = all.stream().filter(e -> "하행".equals(e.getDirection())).toList();

        assertEquals(1, up.size());
        assertEquals(1, down.size());
    }
}
