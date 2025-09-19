package com.example.ganglng.Repository;


import com.example.ganglng.Entity.BusStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusStopRepository extends JpaRepository<BusStop, Integer> {

    @Query(value = "SELECT * FROM bus_stop s WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) * " +
            "cos(radians(s.longitude) - radians(:longitude)) + " +
            "sin(radians(:latitude)) * sin(radians(s.latitude)))) * 0 <= :radius",
            nativeQuery = true)
    List<BusStop> findNearbyStops(@Param("latitude") double latitude,
                                  @Param("longitude") double longitude,
                                  @Param("radius") int radius);
}
