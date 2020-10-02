package chapter4;

import annotation.GuardedBy;
import annotation.ThreadSafe;

@ThreadSafe
public class Ex_4_1_Counter {
    // 하나의 상태 변수만을 갖는 클래스
    @GuardedBy("this")
    private long value = 0;

    public synchronized long getValue() {
        return value;
    }

    public synchronized long increment() {
        if (value == Long.MAX_VALUE) {
            throw new IllegalStateException("counter overflow");
        }

        return ++value;
    }
}
