package chapter5;

import java.util.Map;
import java.util.concurrent.*;

import static chapter5.Ex_5_12_Preloader.launderThrowable;

public class Ex_5_18_Memoizer3<A, V> implements Ex_5_16_Computable<A, V> {
    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Ex_5_16_Computable<A, V> c;

    public Ex_5_18_Memoizer3(Ex_5_16_Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        // 진행중인 작업 가져오기
        Future<V> f = cache.get(arg);

        // 없으면 추가하는 복합 연산을 단일 연산으로 수행해야 한다.
        if (f == null) {
            // 작업을 추가하는 도중에 다른 스레드에서 중복하여 작업을 추가할 수 있다.
            Callable<V> eval = () -> c.compute(arg);
            FutureTask<V> ft = new FutureTask<>(eval);
            f = ft;
            cache.put(arg, ft);
            ft.run(); // c.compute는 이 안에서 호출
        }

        try {
            return f.get();
        } catch (ExecutionException e) {
            throw launderThrowable(e.getCause());
        }
    }
}
