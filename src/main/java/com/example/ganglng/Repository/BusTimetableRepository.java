package com.example.ganglng.repository;

import com.example.ganglng.entity.BusTimetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
@Repository
public interface BusTimetableRepository extends JpaRepository<BusTimetable, Long>{
    List<BusTimetable> findByBusStop_StationNoAndDayTypeAndArrivalTimeAfterOrderByArrivalTimeAsc(
            int stationNo, BusTimetable.DayType dayType, LocalTime time);
}
