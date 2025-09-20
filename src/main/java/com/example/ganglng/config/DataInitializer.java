package com.example.ganglng.config;


import com.example.ganglng.entity.BusRoute;
import com.example.ganglng.entity.BusStop;
import com.example.ganglng.entity.BusTimetable;
import com.example.ganglng.entity.RouteSequence;
import com.example.ganglng.repository.BusRouteRepository;
import com.example.ganglng.repository.BusStopRepository;
import com.example.ganglng.repository.BusTimetableRepository;
import com.example.ganglng.repository.RouteSequenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BusRouteRepository busRouteRepository;
    private final BusStopRepository busStopRepository;
    private final RouteSequenceRepository routeSequenceRepository;
    private final BusTimetableRepository busTimetableRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // --- 1. 버스 정류장(BusStop) 데이터 저장 ---
        busStopRepository.saveAll(Arrays.asList(
                new BusStop(4201209, "공단", 37.76519601, 128.9220542),
                new BusStop(4201205, "입암2.3주공", 37.76507249, 128.916185),
                new BusStop(4200262, "로얄아파트.포남1동주민센터", 37.76545704, 128.9093329),
                new BusStop(4200318, "포남시장", 37.76392282, 128.9076606),
                new BusStop(4200259, "용지각", 37.76184119, 128.9053772),
                new BusStop(4200257, "옥천119안전센터", 37.76104108, 128.9030517),
                new BusStop(4200721, "강릉역", 37.76416331, 128.899258),
                new BusStop(4200315, "구터미널", 37.76163003, 128.898843),
                new BusStop(4201037, "교동철교", 37.75864453, 128.894679),
                new BusStop(4200114, "고용센터", 37.75744085, 128.8889311),
                new BusStop(4200116, "적십자입구", 37.75719636, 128.8872632),
                new BusStop(4200118, "교동주유소", 37.75608209, 128.8840641),
                new BusStop(4200750, "강릉시청앞", 37.7536512, 128.8801331),
                new BusStop(4200121, "강릉시외.고속터미널", 37.75486594, 128.8791162),
                new BusStop(4200119, "교동주유소", 37.75557909, 128.8838114),
                new BusStop(4200117, "적십자입구", 37.75676624, 128.8864721),
                new BusStop(4200115, "고용센터", 37.75714942, 128.8890663),
                new BusStop(4200249, "용강동서부시장", 37.75492248, 128.8916729),
                new BusStop(4200252, "신영극장", 37.75448236, 128.8961288),
                new BusStop(4201199, "남대천.강릉교", 37.75417894, 128.9010687),
                new BusStop(4201197, "강릉중입구", 37.75258618, 128.904274),
                new BusStop(4201195, "노암현대아파트", 37.7503974, 128.9064519),
                new BusStop(4200792, "부흥마을", 37.74678152, 128.9063322),
                new BusStop(4200736, "똑갑재", 37.74299783, 128.9084948),
                new BusStop(4200734, "평안의집", 37.74069819, 128.9050474),
                new BusStop(4200732, "삼흥사", 37.73979897, 128.9011987),
                new BusStop(4200730, "보리사입구", 37.73631226, 128.8977757)
        ));

        // --- 'g' ID와 정류장 번호를 매핑하는 Map 생성 ---
        Map<String, Integer> gIdToStationNoMap = Map.ofEntries(
                Map.entry("g1", 4201209), Map.entry("g2", 4201205), Map.entry("g3", 4200262),
                Map.entry("g4", 4200318), Map.entry("g5", 4200259), Map.entry("g6", 4200257),
                Map.entry("g7", 4200721), Map.entry("g8", 4200315), Map.entry("g9", 4201037),
                Map.entry("g10", 4200114), Map.entry("g11", 4200116), Map.entry("g12", 4200118),
                Map.entry("g13", 4200750), Map.entry("g14", 4200121), Map.entry("g15", 4200119),
                Map.entry("g16", 4200117), Map.entry("g17", 4200115), Map.entry("g18", 4200249),
                Map.entry("g19", 4200252), Map.entry("g20", 4201199), Map.entry("g21", 4201197),
                Map.entry("g22", 4201195), Map.entry("g23", 4200792), Map.entry("g24", 4200736),
                Map.entry("g25", 4200734), Map.entry("g26", 4200732), Map.entry("g27", 4200730)
        );

        // --- 2. 버스 노선(BusRoute) 데이터 저장 ---
        BusRoute routeI1 = new BusRoute("I1", "101", "공단", "학산설래");
        BusRoute routeL2 = new BusRoute("l2", "101", "학산설래", "공단");
        busRouteRepository.saveAll(Arrays.asList(routeI1, routeL2));

        // --- 3. 노선 순서(RouteSequence) 데이터 저장 ---
        Map<Integer, BusStop> savedStops = busStopRepository.findAll().stream()
                .collect(Collectors.toMap(BusStop::getStationNo, stop -> stop));

        String[] sequenceRawData = {
                "n1 I1 g1 1", "n2 I1 g2 2", "n3 I1 g3 3", "n4 I1 g4 4", "n5 I1 g5 5",
                "n6 I1 g6 6", "n7 I1 g7 7", "n8 I1 g8 8", "n9 I1 g9 9", "n10 I1 g10 10",
                "n11 I1 g11 11", "n12 I1 g12 12", "n13 I1 g13 13", "n14 I1 g14 14", "n15 I1 g15 15",
                "n16 I1 g16 16", "n17 I1 g17 17", "n18 I1 g18 18", "n19 I1 g19 19", "n20 I1 g20 20",
                "n21 I1 g21 21", "n22 I1 g22 22", "n23 I1 g23 23", "n24 I1 g24 24", "n25 I1 g25 25",
                "n26 I1 g26 26", "n27 I1 g27 27"
        };
        List<RouteSequence> routeSequences = new ArrayList<>();
        for (String seq : sequenceRawData) {
            String[] parts = seq.split(" ");
            String stopGId = parts[2];
            int order = Integer.parseInt(parts[3]);

            Integer stationNo = gIdToStationNoMap.get(stopGId);
            if (stationNo != null && savedStops.containsKey(stationNo)) {
                routeSequences.add(new RouteSequence(routeI1, savedStops.get(stationNo), order));
            }
        }
        routeSequenceRepository.saveAll(routeSequences);

        // --- 4. 버스 시간표(BusTimetable) 데이터 저장 ---
        String[] timetableRawData = {
                // 평일
                "g1 08:34 평일", "g1 10:33 평일", "g1 12:33 평일", "g1 14:34 평일", "g1 16:33 평일", "g1 18:33 평일",
                "g2 08:36 평일", "g2 10:35 평일", "g2 12:35 평일", "g2 14:36 평일", "g2 16:35 평일", "g2 18:35 평일",
                "g3 08:40 평일", "g3 10:39 평일", "g3 12:39 평일", "g3 14:40 평일", "g3 16:39 평일", "g3 18:39 평일",
                "g4 08:40 평일", "g4 10:39 평일", "g4 12:39 평일", "g4 14:40 평일", "g4 16:39 평일", "g4 18:39 평일",
                "g5 08:42 평일", "g5 10:41 평일", "g5 12:41 평일", "g5 14:42 평일", "g5 16:41 평일", "g5 18:41 평일",
                "g6 08:42 평일", "g6 10:41 평일", "g6 12:41 평일", "g6 14:42 평일", "g6 16:41 평일", "g6 18:41 평일",
                "g7 08:44 평일", "g7 10:43 평일", "g7 12:43 평일", "g7 14:44 평일", "g7 16:43 평일", "g7 18:43 평일",
                "g8 08:46 평일", "g8 10:45 평일", "g8 12:45 평일", "g8 14:46 평일", "g8 16:45 평일", "g8 18:45 평일",
                "g9 08:47 평일", "g9 10:46 평일", "g9 12:46 평일", "g9 14:47 평일", "g9 16:46 평일", "g9 18:46 평일",
                "g10 08:49 평일", "g10 10:48 평일", "g10 12:48 평일", "g10 14:49 평일", "g10 16:48 평일", "g10 18:48 평일",
                "g11 08:49 평일", "g11 10:48 평일", "g11 12:48 평일", "g11 14:49 평일", "g11 16:48 평일", "g11 18:48 평일",
                "g12 08:51 평일", "g12 10:50 평일", "g12 12:50 평일", "g12 14:51 평일", "g12 16:50 평일", "g12 18:50 평일",
                "g13 08:52 평일", "g13 10:51 평일", "g13 12:51 평일", "g13 14:52 평일", "g13 16:51 평일", "g13 18:51 평일",
                "g14 08:54 평일", "g14 10:53 평일", "g14 12:53 평일", "g14 14:54 평일", "g14 16:53 평일", "g14 18:53 평일",
                "g15 08:56 평일", "g15 10:55 평일", "g15 12:55 평일", "g15 14:56 평일", "g15 16:55 평일", "g15 18:55 평일",
                "g16 08:58 평일", "g16 10:57 평일", "g16 12:57 평일", "g16 14:58 평일", "g16 16:57 평일", "g16 18:57 평일",
                "g17 08:58 평일", "g17 10:57 평일", "g17 12:57 평일", "g17 14:58 평일", "g17 16:57 평일", "g17 18:57 평일",
                "g18 08:59 평일", "g18 10:58 평일", "g18 12:58 평일", "g18 14:59 평일", "g18 16:58 평일", "g18 18:58 평일",
                "g19 09:03 평일", "g19 11:02 평일", "g19 13:02 평일", "g19 15:03 평일", "g19 17:02 평일", "g19 19:02 평일",
                "g20 09:07 평일", "g20 11:06 평일", "g20 13:06 평일", "g20 15:07 평일", "g20 17:06 평일", "g20 19:06 평일",
                "g21 09:10 평일", "g21 11:09 평일", "g21 13:09 평일", "g21 15:10 평일", "g21 17:09 평일", "g21 19:09 평일",
                "g22 09:10 평일", "g22 11:09 평일", "g22 13:09 평일", "g22 15:10 평일", "g22 17:09 평일", "g22 19:09 평일",
                "g23 09:11 평일", "g23 11:10 평일", "g23 13:10 평일", "g23 15:11 평일", "g23 17:10 평일", "g23 19:10 평일",
                "g24 09:12 평일", "g24 11:11 평일", "g24 13:11 평일", "g24 15:12 평일", "g24 17:11 평일", "g24 19:11 평일",
                "g25 09:13 평일", "g25 11:12 평일", "g25 13:12 평일", "g25 15:13 평일", "g25 17:12 평일", "g25 19:12 평일",
                "g26 09:14 평일", "g26 11:13 평일", "g26 13:13 평일", "g26 15:14 평일", "g26 17:13 평일", "g26 19:13 평일",
                "g27 09:15 평일", "g27 11:14 평일", "g27 13:14 평일", "g27 15:15 평일", "g27 17:14 평일", "g27 19:14 평일",

                // 주말
                "g1 08:34 주말", "g1 10:33 주말", "g1 12:33 주말", "g1 14:32 주말", "g1 16:32 주말", "g1 18:31 주말",
                "g2 08:36 주말", "g2 10:35 주말", "g2 12:35 주말", "g2 14:34 주말", "g2 16:34 주말", "g2 18:33 주말",
                "g3 08:40 주말", "g3 10:39 주말", "g3 12:39 주말", "g3 14:38 주말", "g3 16:38 주말", "g3 18:37 주말",
                "g4 08:40 주말", "g4 10:39 주말", "g4 12:39 주말", "g4 14:38 주말", "g4 16:38 주말", "g4 18:37 주말",
                "g5 08:42 주말", "g5 10:41 주말", "g5 12:41 주말", "g5 14:40 주말", "g5 16:40 주말", "g5 18:39 주말",
                "g6 08:42 주말", "g6 10:41 주말", "g6 12:41 주말", "g6 14:40 주말", "g6 16:40 주말", "g6 18:39 주말",
                "g7 08:44 주말", "g7 10:43 주말", "g7 12:43 주말", "g7 14:42 주말", "g7 16:42 주말", "g7 18:41 주말",
                "g8 08:46 주말", "g8 10:45 주말", "g8 12:45 주말", "g8 14:44 주말", "g8 16:44 주말", "g8 18:43 주말",
                "g9 08:47 주말", "g9 10:46 주말", "g9 12:46 주말", "g9 14:45 주말", "g9 16:45 주말", "g9 18:44 주말",
                "g10 08:49 주말", "g10 10:48 주말", "g10 12:48 주말", "g10 14:47 주말", "g10 16:47 주말", "g10 18:46 주말",
                "g11 08:49 주말", "g11 10:48 주말", "g11 12:48 주말", "g11 14:47 주말", "g11 16:47 주말", "g11 18:46 주말",
                "g12 08:51 주말", "g12 10:50 주말", "g12 12:50 주말", "g12 14:49 주말", "g12 16:49 주말", "g12 18:48 주말",
                "g13 08:52 주말", "g13 10:51 주말", "g13 12:51 주말", "g13 14:50 주말", "g13 16:50 주말", "g13 18:49 주말",
                "g14 08:54 주말", "g14 10:53 주말", "g14 12:53 주말", "g14 14:52 주말", "g14 16:52 주말", "g14 18:51 주말",
                "g15 08:56 주말", "g15 10:55 주말", "g15 12:55 주말", "g15 14:54 주말", "g15 16:54 주말", "g15 18:53 주말",
                "g16 08:58 주말", "g16 10:57 주말", "g16 12:57 주말", "g16 14:56 주말", "g16 16:56 주말", "g16 18:55 주말",
                "g17 08:58 주말", "g17 10:57 주말", "g17 12:57 주말", "g17 14:56 주말", "g17 16:56 주말", "g17 18:55 주말",
                "g18 08:59 주말", "g18 10:58 주말", "g18 12:58 주말", "g18 14:57 주말", "g18 16:57 주말", "g18 18:56 주말",
                "g19 09:03 주말", "g19 11:02 주말", "g19 13:02 주말", "g19 15:01 주말", "g19 17:01 주말", "g19 19:00 주말",
                "g20 09:07 주말", "g20 11:06 주말", "g20 13:06 주말", "g20 15:05 주말", "g20 17:05 주말", "g20 19:04 주말",
                "g21 09:10 주말", "g21 11:09 주말", "g21 13:09 주말", "g21 15:08 주말", "g21 17:08 주말", "g21 19:07 주말",
                "g22 09:10 주말", "g22 11:09 주말", "g22 13:09 주말", "g22 15:08 주말", "g22 17:08 주말", "g22 19:07 주말",
                "g23 09:11 주말", "g23 11:10 주말", "g23 13:10 주말", "g23 15:09 주말", "g23 17:09 주말", "g23 19:08 주말",
                "g24 09:12 주말", "g24 11:11 주말", "g24 13:11 주말", "g24 15:10 주말", "g24 17:10 주말", "g24 19:09 주말",
                "g25 09:13 주말", "g25 11:12 주말", "g25 13:12 주말", "g25 15:11 주말", "g25 17:11 주말", "g25 19:10 주말",
                "g26 09:14 주말", "g26 11:13 주말", "g26 13:13 주말", "g26 15:12 주말", "g26 17:12 주말", "g26 19:11 주말",
                "g27 09:15 주말", "g27 11:14 주말", "g27 13:14 주말", "g27 15:13 주말", "g27 17:13 주말", "g27 19:12 주말"
        };

        List<BusTimetable> busTimetables = new ArrayList<>();
        for (String entry : timetableRawData) {
            String[] parts = entry.split(" ");
            String stopGId = parts[0];
            LocalTime time = LocalTime.parse(parts[1]);
            BusTimetable.DayType dayType = parts[2].equals("평일") ? BusTimetable.DayType.WEEKDAY : BusTimetable.DayType.WEEKEND;

            Integer stationNo = gIdToStationNoMap.get(stopGId);
            if (stationNo != null && savedStops.containsKey(stationNo)) {
                busTimetables.add(new BusTimetable(routeI1, savedStops.get(stationNo), time, dayType));
            }
        }
        busTimetableRepository.saveAll(busTimetables);
    }
}