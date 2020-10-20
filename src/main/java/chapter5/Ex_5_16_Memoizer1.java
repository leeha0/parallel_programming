package chapter5;

import net.jcip.annotations.GuardedBy;

import java.util.HashMap;
import java.util.Map;

public class Ex_5_16_Memoizer1<A, V> implements Ex_5_16_Computable<A, V> {

    // HashMap은 스레드 안전하지 않다.
    @GuardedBy("this")
    private final Map<A, V> cache = new HashMap<>();
    private final Ex_5_16_Computable<A, V> c;

    public Ex_5_16_Memoizer1(Ex_5_16_Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        // HashMap을 동기화 하기 위해 compute 메소드 전체를 동기화 함
        V result = cache.get(arg);
        if (result == null) {
            result = c.compute(arg);
            cache.put(arg, result);
        }
        return result;
    }
}
