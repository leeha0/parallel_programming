package chapter4;

import annotation.Immutable;

@Immutable
public class Ex_4_6_Point {
    // 불변 변수는 안전하게 외부에 공개할 수 있어 스레드 안전하다.
    public final int x;
    public final int y;

    public Ex_4_6_Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
