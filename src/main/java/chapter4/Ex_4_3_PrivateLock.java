package chapter4;

import net.jcip.annotations.GuardedBy;

public class Ex_4_3_PrivateLock {

    // private final 객체를 이용하여 락으로 활용하여 모니터 패턴 구현
    @GuardedBy("this")
    private final Object myLock = new Object();
    Ex_4_3_Widget ex43Widget;

    void someMethod() {
        synchronized (myLock) {
            // widget 변수의 값을 읽거나 변경
        }
    }
}
