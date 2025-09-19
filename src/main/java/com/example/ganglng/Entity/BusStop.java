package com.example.ganglng.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BusStop {
    @Id // 정류장 번호를 기본 키(PK)로 사용
    @Column(name = "station_no")
    private int stationNo;

    @Column(name = "station_name")
    private String stationName;

    @Column(name = "latitude")
    private double latitude; // y

    @Column(name = "longitude")
    private double longitude; // x

    public BusStop(int stationNo, String stationName, double latitude, double longitude) {
        this.stationNo = stationNo;
        this.stationName = stationName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

