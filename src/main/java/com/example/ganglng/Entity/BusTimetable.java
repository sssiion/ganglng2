package com.example.ganglng.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class BusTimetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private BusRoute busRoute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_no")
    private BusStop busStop;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_type")
    private DayType dayType; // 평일, 주말

    public enum DayType {
        WEEKDAY, WEEKEND
    }

    public BusTimetable(BusRoute busRoute, BusStop busStop, LocalTime arrivalTime, DayType dayType) {
        this.busRoute = busRoute;
        this.busStop = busStop;
        this.arrivalTime = arrivalTime;
        this.dayType = dayType;
    }
}
