package chapter4;

import annotation.ThreadSafe;
import net.jcip.annotations.GuardedBy;

@ThreadSafe
public class Ex_4_11_SafePoint {

    @GuardedBy("this")
    private int x;
    @GuardedBy("this")
    private int y;

    // 복사 생성 메소드가 this(p.x, p.y)와 같이 구현되어 있을 경우 발생할 수 있는 경쟁 조건을 방지하기 위해 private로 선언
    private Ex_4_11_SafePoint(int[] a) {
        this(a[0], a[1]);
    }

    public Ex_4_11_SafePoint(int x, int y) {
        this.set(x, y);
    }

    public synchronized void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public synchronized int[] get() {
        return new int[]{x, y};
    }
}
