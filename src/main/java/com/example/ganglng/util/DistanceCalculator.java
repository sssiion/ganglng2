package com.example.ganglng.util;

public class DistanceCalculator {
    // 지구의 반지름 (단위: km)
    private static final int EARTH_RADIUS_KM = 6371;

    /**
     * 두 지점 간의 거리를 'Haversine' 공식을 사용하여 계산합니다.
     *
     * @param lat1 지점 1의 위도
     * @param lon1 지점 1의 경도
     * @param lat2 지점 2의 위도
     * @param lon2 지점 2의 경도
     * @return 두 지점 간의 거리 (km 단위)
     */
    public static double calculate(double lat1, double lon1, double lat2, double lon2) {
        // 위도와 경도를 라디안 값으로 변환
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        // Haversine 공식 적용
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 최종 거리 계산
        return EARTH_RADIUS_KM * c;
    }
}
