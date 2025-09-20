package com.example.ganglng.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BusRoute {
    @Id
    @Column(name = "route_id")
    private String routeId; // 예: "I1"

    @Column(name = "route_no")
    private String routeNo; // 예: "101"

    @Column(name = "start_point")
    private String startPoint;

    @Column(name = "end_point")
    private String endPoint;

    public BusRoute(String routeId, String routeNo, String startPoint, String endPoint) {
        this.routeId = routeId;
        this.routeNo = routeNo;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
}
