package com.example.ganglng.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class RouteSequence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id")
    private BusRoute busRoute;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_no")
    private BusStop busStop;

    @Column(name = "sequence_order") // DB 컬럼 이름도 바꿔주는 것이 좋습니다.
    private int sequenceOrder;

    public RouteSequence(BusRoute busRoute, BusStop busStop, int sequenceOrder) {
        this.busRoute = busRoute;
        this.busStop = busStop;
        this.sequenceOrder = sequenceOrder;
    }
}
