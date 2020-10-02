package chapter4;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Ex_4_8_DelegatingVehicleTracker2 {

    private final ConcurrentMap<String, Ex_4_6_Point> locations;

    public Ex_4_8_DelegatingVehicleTracker2(ConcurrentMap<String, Ex_4_6_Point> points) {
        this.locations = new ConcurrentHashMap<>(points);
    }

    public Map<String, Ex_4_6_Point> getLocations() {
        // 변경 불가능한 객체를 공유하여 스레드 안전성을 확보
        // 특정 시점의 고정된 데이터를 공개
        return Collections.unmodifiableMap(locations); // 단순 복사본
    }

    public Ex_4_6_Point getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (locations.replace(id, new Ex_4_6_Point(x, y)) == null) {
            throw new IllegalArgumentException("invalid vehicle name: " + id);
        }
    }
}
