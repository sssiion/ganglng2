package com.example.ganglng.rervice;

import com.example.ganglng.entity.BusStop;
import com.example.ganglng.entity.BusTimetable;
import com.example.ganglng.dto.RecommendationResponse;
import com.example.ganglng.dto.TourAttraction;
import com.example.ganglng.util.DistanceCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final BusService busService;
    private final TourApiService tourApiService;

    public List<RecommendationResponse> recommend(double userLat, double userLon, String keyword, String timeStr) {
        System.out.println("\n--- ✅ 추천 로직 시작 ---");
        System.out.println("입력값: lat=" + userLat + ", lon=" + userLon + ", keyword=" + keyword + ", time=" + timeStr);

        LocalTime searchTime;
        if (timeStr != null && !timeStr.isEmpty()) {
            searchTime = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            searchTime = LocalTime.now();
        }
        System.out.println("검색 기준 시간: " + searchTime);

        List<BusStop> nearbyStops = busService.findNearbyStops(userLat, userLon, 400);
        System.out.println("1. 주변 정류장 검색 결과: " + nearbyStops.size() + "개");
        if (nearbyStops.isEmpty()) {
            System.out.println("--- ❌ 추천 로직 중단: 주변 정류장 없음 ---");
            return List.of();
        }

        BusTimetable upcomingBus = null;
        for (BusStop stop : nearbyStops) {
            List<BusTimetable> upcomingBuses = busService.findUpcomingBuses(stop.getStationNo(), searchTime);
            if (!upcomingBuses.isEmpty()) {
                upcomingBus = upcomingBuses.get(0);
                break;
            }
        }
        System.out.println("2. 가장 빠른 버스: " + (upcomingBus != null ? upcomingBus.getBusRoute().getRouteNo() + "번" : "찾지 못함"));
        if (upcomingBus == null) {
            System.out.println("--- ❌ 추천 로직 중단: 이용 가능한 버스 없음 ---");
            return List.of();
        }

        List<BusStop> next5Stops = busService.findNextStops(
                upcomingBus.getBusRoute().getRouteId(),
                upcomingBus.getBusStop().getStationNo(),
                5);
        System.out.println("3. 버스로 갈 다음 정류장: " + next5Stops.stream().map(BusStop::getStationName).collect(Collectors.toList()));

        // ✅ 4. [수정] 위치 기반 관광지 목록 조합 (총 6번 호출)
        // 4-1. 사용자 현재 위치 기반 검색 (1번)
        List<TourAttraction> userLocationAttractions = tourApiService.fetchAttractionsByUserLocation(userLat, userLon);
        System.out.println("4-1. 사용자 위치 기반 관광지: " + userLocationAttractions.stream().map(TourAttraction::getName).collect(Collectors.toList()));

        // 4-2. 5개 버스 정류장 주변 검색 (5번)
        List<TourAttraction> busStopAttractions = tourApiService.fetchAttractionsByLocation(next5Stops);
        System.out.println("4-2. 버스 정류장 기반 관광지: " + busStopAttractions.stream().map(TourAttraction::getName).collect(Collectors.toList()));

        // 4-3. 두 목록을 합치고 중복 제거하여 A 목록 생성
        List<TourAttraction> busBasedAttractions = Stream.concat(userLocationAttractions.stream(), busStopAttractions.stream())
                .distinct()
                .collect(Collectors.toList());
        System.out.println("4. [A 목록 최종] 위치 기반 관광지: " + busBasedAttractions.stream().map(TourAttraction::getName).collect(Collectors.toList()));

        List<TourAttraction> keywordBasedAttractions = tourApiService.fetchAttractionsByKeyword(keyword);
        System.out.println("5. [B 목록] 키워드 기반 관광지: " + keywordBasedAttractions.stream().map(TourAttraction::getName).collect(Collectors.toList()));

        List<TourAttraction> finalAttractions = busBasedAttractions.stream()
                .filter(keywordBasedAttractions::contains)
                .distinct()
                .toList();
        System.out.println("6. [최종 교집합 결과]: " + finalAttractions.stream().map(TourAttraction::getName).collect(Collectors.toList()));
        System.out.println("--- ✅ 추천 로직 종료 ---\n");

        return finalAttractions.stream()
                .map(attraction -> new RecommendationResponse(
                        attraction.getName(),
                        attraction.getAddress(),
                        attraction.getLatitude(),
                        attraction.getLongitude(),
                        DistanceCalculator.calculate(userLat, userLon, attraction.getLatitude(), attraction.getLongitude())
                ))
                .sorted(Comparator.comparingDouble(RecommendationResponse::getDistance))
                .collect(Collectors.toList());
    }
}
