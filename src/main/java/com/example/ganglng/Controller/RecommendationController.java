package com.example.ganglng.controller;



import com.example.ganglng.rervice.RecommendationService;
import com.example.ganglng.dto.RecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/api/recommendations")
    public ResponseEntity<List<RecommendationResponse>> getRecommendations(
            @RequestParam("lat") double latitude,
            @RequestParam("lon") double longitude,
            @RequestParam("keyword") String keyword,
            @RequestParam(required = false) String time
    ) {
        List<RecommendationResponse> recommendations =
                recommendationService.recommend(latitude, longitude, keyword, time);
        return ResponseEntity.ok(recommendations);
    }
}
