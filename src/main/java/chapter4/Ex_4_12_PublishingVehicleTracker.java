package chapter4;

import annotation.ThreadSafe;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class Ex_4_12_PublishingVehicleTracker {

    private final Map<String, Ex_4_11_SafePoint> locations;
    private final Map<String, Ex_4_11_SafePoint> unmodifiableMap;

    // ConcurrentHashMap 클래스에게 스레드 안정성 위임
    public Ex_4_12_PublishingVehicleTracker(Map<String, Ex_4_11_SafePoint> locations, Map<String, Ex_4_11_SafePoint> unmodifiableMap) {
        this.locations = new ConcurrentHashMap<>(locations);
        this.unmodifiableMap = Collections.unmodifiableMap(this.locations);
    }

    // 스레드에 안전하고 변경 가능한 SafePoint 클래스를 외부에 공개
    public Map<String, Ex_4_11_SafePoint> getLocations() {
        return unmodifiableMap;
    }

    public Ex_4_11_SafePoint getLocation(String id) {
        return locations.get(id);
    }

    public void setLocation(String id, int x, int y) {
        if (!locations.containsKey(id)) {
            throw new IllegalArgumentException("invalid vehicle name: " + id);
        }
        locations.get(id).set(x, y);
    }
}
