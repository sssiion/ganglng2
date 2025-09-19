package com.example.ganglng.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TourAttraction {

    // 1. 필드 (Fields)
    private String name;        // 관광지 이름
    private String address;     // 관광지 주소
    private double latitude;    // 위도
    private double longitude;   // 경도

    // 2. equals() 메서드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourAttraction that = (TourAttraction) o;
        // 두 객체의 'name' 필드가 같으면 같은 객체로 판단
        return Objects.equals(name, that.name);
    }

    // 3. hashCode() 메서드
    @Override
    public int hashCode() {
        // equals()에서 이름만 비교했으므로, hashCode()도 이름 기반으로 생성
        return Objects.hash(name);
    }
}
