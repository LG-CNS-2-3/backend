package com.mini.mini_2.member.presentation;

import com.mini.mini_2.member.application.FavoriteApplicationService;
import com.mini.mini_2.member.application.dto.command.AddFavoriteCommand;
import com.mini.mini_2.member.application.dto.response.FavoriteResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members/{memberId}/favorites")
@RequiredArgsConstructor
@Tag(name = "Favorite API", description = "즐겨찾기 API")
public class FavoriteController {

    private final FavoriteApplicationService favoriteApplicationService;

    @Operation(
        summary = "즐겨찾기 추가",
        description = "휴게소를 즐겨찾기에 추가합니다."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "추가 성공"),
        @ApiResponse(responseCode = "404", description = "회원 또는 휴게소를 찾을 수 없음")
    })
    @PostMapping
    public ResponseEntity<FavoriteResponse> addFavorite(
        @PathVariable Long memberId,
        @RequestBody AddFavoriteCommand command
    ) {
        AddFavoriteCommand fullCommand = new AddFavoriteCommand(
            memberId, // command에 memberId가 포함되어야 함
            command.restAreaId(),
            command.description()
        );

        FavoriteResponse response = favoriteApplicationService.addFavorite(fullCommand);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
        summary = "즐겨찾기 목록 조회",
        description = "회원의 즐겨찾기 목록을 조회합니다."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getFavorites(@PathVariable Long memberId) {
        List<FavoriteResponse> responses = favoriteApplicationService.getFavorites(memberId);
        return ResponseEntity.ok(responses);
    }

    @Operation(
        summary = "즐겨찾기 삭제",
        description = "즐겨찾기를 삭제합니다."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "삭제 성공")
    })
    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long favoriteId) {
        favoriteApplicationService.removeFavorite(favoriteId);
        return ResponseEntity.noContent().build();
    }
}
