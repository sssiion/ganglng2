package com.example.ganglng.Repository;

import com.example.ganglng.Entity.RouteSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RouteSequenceRepository extends JpaRepository<RouteSequence, Long> {
    Optional<RouteSequence> findByBusRoute_RouteIdAndBusStop_StationNo(String routeId, int stationNo);

    List<RouteSequence> findByBusRoute_RouteIdAndSequenceOrderGreaterThanOrderBySequenceOrderAsc(
            String routeId, int currentSequence);

}
