package com.example.ganglng.rervice;

import com.example.ganglng.entity.BusStop;
import com.example.ganglng.dto.ApiResponse;
import com.example.ganglng.dto.TourAttraction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TourApiService {

    private final RestTemplate restTemplate;
    private final String serviceKey;
    private final String baseUrl;

    // application.properties에서 설정값 주입
    public TourApiService(RestTemplate restTemplate,
                          @Value("${api.serviceKey}") String serviceKey,
                          @Value("${api.baseUrl}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.serviceKey = serviceKey;
        this.baseUrl = baseUrl;
    }
    /**
     * ✅ [신규] 사용자 현재 위치 기반으로 관광 정보를 조회합니다.
     */
    public List<TourAttraction> fetchAttractionsByUserLocation(double lat, double lon) {
        return callLocationBasedApi(lat, lon);
    }

    /**
     * ✅ [수정] 5개의 버스 정류장 각각의 위치 기반으로 관광 정보를 조회하고 결과를 합칩니다.
     */
    public List<TourAttraction> fetchAttractionsByLocation(List<BusStop> stops) {
        if (stops == null || stops.isEmpty()) {
            return Collections.emptyList();
        }

        // 5개 정류장에 대해 각각 API를 호출하고, 모든 결과를 하나의 리스트로 합칩니다.
        return stops.stream()
                .map(stop -> callLocationBasedApi(stop.getLatitude(), stop.getLongitude()))
                .flatMap(List::stream) // 여러 리스트를 하나의 스트림으로 평탄화
                .distinct() // 중복된 관광지 제거
                .collect(Collectors.toList());
    }



    /**
     * ✅ [신규] 위치 기반 API를 호출하는 공통 로직
     */
    private List<TourAttraction> callLocationBasedApi(double lat, double lon) {
        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/locationBasedList2")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 20) // 호출 당 결과 수를 줄여서 다양한 결과를 얻음
                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "GangneungTravel")
                .queryParam("_type", "json")
                .queryParam("arrange", "E")
                .queryParam("mapX", lon)
                .queryParam("mapY", lat)
                .queryParam("radius", 2000) // 반경 2km
                .queryParam("contentTypeId", 15)
                .build(true)
                .toUri();

        return callApiAndParseResponse(uri);
    }


    /**
     * 키워드로 관광 정보를 조회합니다.
     * @param keyword 검색할 키워드
     * @return 관광지 목록
     */
    public List<TourAttraction> fetchAttractionsByKeyword(String keyword) {
        // 강릉 지역코드: 32, 시군구코드: 1
        final String GANGNEUNG_AREA_CODE = "32";
        final String GANGNEUNG_SIGUNGU_CODE = "1";

        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/searchKeyword2")
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 100)
                .queryParam("pageNo", 1)
                .queryParam("MobileOS", "ETC")
                .queryParam("MobileApp", "GangneungTravel")
                .queryParam("_type", "json")
                .queryParam("arrange", "A") // 제목순 정렬
                .queryParam("keyword", URLEncoder.encode(keyword, StandardCharsets.UTF_8))
                .queryParam("areaCode", GANGNEUNG_AREA_CODE)
                .queryParam("sigunguCode", GANGNEUNG_SIGUNGU_CODE)
                .build(true)
                .toUri();

        return callApiAndParseResponse(uri);
    }

    // 공통 API 호출 및 파싱 로직
    private List<TourAttraction> callApiAndParseResponse(URI uri) {
        try {
            ApiResponse response = restTemplate.getForObject(uri, ApiResponse.class);
            if (response != null && response.getResponse().getBody().getItems() != null) {
                return response.getResponse().getBody().getItems().getItem().stream()
                        .map(item -> new TourAttraction(
                                item.getTitle(),
                                item.getAddr1(),
                                item.getMapy(), // 위도
                                item.getMapx()  // 경도
                        ))
                        .collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.error("API 호출 중 오류 발생: URI={}", uri, e);
        }
        return Collections.emptyList();
    }
}
