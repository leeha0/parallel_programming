package chapter4;

import annotation.ThreadSafe;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ThreadSafe
public class Ex_4_7_DelegatingVehicleTracker1 {

    private final ConcurrentMap<String, Ex_4_6_Point> locations;
    private final Map<String, Ex_4_6_Point> unmodifiableMap;

    public Ex_4_7_DelegatingVehicleTracker1(ConcurrentMap<String, Ex_4_6_Point> points) {
        this.locations = new ConcurrentHashMap<>(points);
        this.unmodifiableMap = Collections.unmodifiableMap(locations);
    }

    public Map<String, Ex_4_6_Point> getLocations() {
        // 변경 불가능한 객체를 공유하여 스레드 안전성을 확보
        // 변경된 데이터를 공개: 서로 다른 스레드간의 변경 값을 실시간으로 확인 가능
        return unmodifiableMap;
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
