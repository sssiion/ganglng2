package com.example.ganglng.rervice;
import com.example.ganglng.entity.BusStop;
import com.example.ganglng.entity.BusTimetable;
import com.example.ganglng.entity.RouteSequence;
import com.example.ganglng.repository.BusStopRepository;
import com.example.ganglng.repository.BusTimetableRepository;
import com.example.ganglng.repository.RouteSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusService {
    private final BusStopRepository busStopRepository;
    private final BusTimetableRepository timetableRepository;
    private final RouteSequenceRepository sequenceRepository;

    /**
     * 특정 좌표 기준으로 반경 내 버스 정류장을 찾습니다.
     * @param latitude 위도
     * @param longitude 경도
     * @param radius 검색 반경 (미터 단위)
     * @return 버스 정류장 목록
     */
    public List<BusStop> findNearbyStops(double latitude, double longitude, int radius) {
        return busStopRepository.findNearbyStops(latitude, longitude, radius);
    }

    /**
     * 특정 정류장에서 현재 시간 이후에 가장 빨리 도착할 버스들을 찾습니다.
     * @param stationNo 정류장 번호
     * @return 도착 예정인 버스 시간표 목록
     */
    public List<BusTimetable> findUpcomingBuses(int stationNo) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        // 주말(토, 일)과 평일을 구분
        BusTimetable.DayType dayType = (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
                ? BusTimetable.DayType.WEEKEND : BusTimetable.DayType.WEEKDAY;

        return timetableRepository.findByBusStop_StationNoAndDayTypeAndArrivalTimeAfterOrderByArrivalTimeAsc(
                stationNo, dayType, LocalTime.now());
    }

    /**
     * 특정 노선의 현재 정류장 다음 순서의 정류장 목록을 찾습니다.
     * @param routeId 노선 ID
     * @param currentStationNo 현재 정류장 번호
     * @param limit 가져올 정류장 개수
     * @return 다음 정류장 목록
     */
    public List<BusStop> findNextStops(String routeId, int currentStationNo, int limit) {
        // 현재 정류장의 노선 순서 정보를 DB에서 조회
        Optional<RouteSequence> currentSequenceOpt = sequenceRepository.findByBusRoute_RouteIdAndBusStop_StationNo(routeId, currentStationNo);

        // 순서 정보가 없으면 빈 리스트 반환
        if (currentSequenceOpt.isEmpty()) {
            return Collections.emptyList();
        }

        int currentOrder = currentSequenceOpt.get().getSequenceOrder();

        // 현재 순서보다 큰 순서의 정류장들을 오름차순으로 조회
        List<RouteSequence> nextSequences = sequenceRepository.findByBusRoute_RouteIdAndSequenceOrderGreaterThanOrderBySequenceOrderAsc(routeId, currentOrder);

        // limit 개수만큼 잘라서 BusStop 객체 목록으로 변환하여 반환
        return nextSequences.stream()
                .limit(limit)
                .map(RouteSequence::getBusStop)
                .collect(Collectors.toList());
    }
    // ✅ LocalTime 파라미터 추가
    public List<BusTimetable> findUpcomingBuses(int stationNo, LocalTime searchTime) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();
        BusTimetable.DayType dayType = (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)
                ? BusTimetable.DayType.WEEKEND : BusTimetable.DayType.WEEKDAY;

        // ✅ 파라미터로 받은 searchTime 사용
        return timetableRepository.findByBusStop_StationNoAndDayTypeAndArrivalTimeAfterOrderByArrivalTimeAsc(
                stationNo, dayType, searchTime);
    }
}
