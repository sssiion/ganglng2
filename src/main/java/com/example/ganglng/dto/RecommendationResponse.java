package com.example.ganglng.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecommendationResponse {

    private String name;        // 관광지 이름
    private String address;     // 주소
    private double latitude;    // 위도
    private double longitude;   // 경도
    private double distance;    // 사용자와의 거리 (km)
}
