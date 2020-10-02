package chapter4;

import annotation.NotThreadSafe;

@NotThreadSafe
public class Ex_4_5_MutablePoint {

    public int x;
    public int y;

    public Ex_4_5_MutablePoint() {
        x = 0;
        y = 0;
    }

    public Ex_4_5_MutablePoint(Ex_4_5_MutablePoint p) {
        this.x = p.x;
        this.y = p.y;
    }
}
