package com.lgcns.backend_map.presentation;

import com.lgcns.backend_map.application.MapService;
import com.lgcns.backend_map.dto.request.RouteSearchReqDTO;
import com.lgcns.backend_map.dto.response.PlaceSearchResDTO;
import com.lgcns.backend_map.dto.response.RouteSearchResDTO;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/maps")
@RequiredArgsConstructor
public class MapController implements MapSwagger {
    private final MapService mapService;

    @GetMapping("/place/search")
    public ResponseEntity<PlaceSearchResDTO> searchPlace(@RequestParam @NotNull String query){
        PlaceSearchResDTO resDTO = mapService.searchPlace(query);

        return ResponseEntity.status(HttpStatus.OK).body(resDTO);
    }

    @GetMapping("/route/search")
    public ResponseEntity<RouteSearchResDTO> searchRoute(@ModelAttribute RouteSearchReqDTO reqDTO){
        RouteSearchResDTO resDTO = mapService.searchRoute(reqDTO);

        return ResponseEntity.status(HttpStatus.OK).body(resDTO);
    }
}
