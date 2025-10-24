package com.lgcns.backend_map.presentation;

import com.lgcns.backend_map.dto.response.PlaceSearchResDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface MapSwagger {

    @Operation(
            summary = "장소 검색 API",
            description = "사용자가 입력한 검색어(query)에 따라 장소 정보를 검색합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "검색 성공",
                    content = @Content(schema = @Schema(implementation = PlaceSearchResDTO.class))
            ),
            @ApiResponse(responseCode = "503", description = "외부 API 서버 오류")
    })

    ResponseEntity<PlaceSearchResDTO> searchPlace(@RequestParam String query);
}
