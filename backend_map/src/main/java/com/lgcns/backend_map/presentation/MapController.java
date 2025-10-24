package com.lgcns.backend_map.presentation;

import com.lgcns.backend_map.application.MapService;
import com.lgcns.backend_map.dto.response.PlaceSearchResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maps")
@RequiredArgsConstructor
public class MapController implements MapSwagger {
    private final MapService mapService;

    @GetMapping("/place/search")
    public ResponseEntity<PlaceSearchResDTO> searchPlace(@RequestParam String query){
        PlaceSearchResDTO resDTO = mapService.searchPlace(query);

        return ResponseEntity.status(HttpStatus.OK).body(resDTO);
    }
}
