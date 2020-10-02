package chapter4;

import annotation.GuardedBy;
import annotation.ThreadSafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class Ex_4_4_MonitorVehicleTracker {

    @GuardedBy("this")
    private final Map<String, Ex_4_5_MutablePoint> locations;

    public Ex_4_4_MonitorVehicleTracker(Map<String, Ex_4_5_MutablePoint> locations) {
        this.locations = locations;
    }

    public synchronized Map<String, Ex_4_5_MutablePoint> getLocations() {
        return deepCopy(locations); // MutablePoint 객체를 완전히 복사하여 클라이언트에게 넘겨주기 때문에 스레드 안전하다.
    }

    public synchronized Ex_4_5_MutablePoint getLocation(String id) {
        Ex_4_5_MutablePoint loc = locations.get(id);
        return loc == null ? null : new Ex_4_5_MutablePoint(loc);
    }

    public synchronized void setLocation(String id, int x, int y) {
        Ex_4_5_MutablePoint loc = locations.get(id);
        if (loc == null)
            throw new IllegalArgumentException("No such ID: " + id);
        loc.x = x;
        loc.y = y;
    }

    private static Map<String, Ex_4_5_MutablePoint> deepCopy(Map<String, Ex_4_5_MutablePoint> m) {
        Map<String, Ex_4_5_MutablePoint> result = new HashMap<>();
        for (String id : m.keySet()) {
            result.put(id, new Ex_4_5_MutablePoint(m.get(id)));
        }

        return Collections.unmodifiableMap(result);
    }
}
