package chapter5;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Ex_5_17_Memoizer2<A, V> implements Ex_5_16_Computable<A, V> {
    // ConcurrentHashMap은 스레드 안전하다.
    private final Map<A, V> cache = new ConcurrentHashMap<>();
    private final Ex_5_16_Computable<A, V> c;

    public Ex_5_17_Memoizer2(Ex_5_16_Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        V result = cache.get(arg);

        if (result == null) {
            // 결고가 값이 없는지 확인하는 도중에 다른 스레드에서 중복 연산이 발생할 수 있다.
            result = c.compute(arg);
            cache.put(arg, result);
        }

        return result;
    }
}
