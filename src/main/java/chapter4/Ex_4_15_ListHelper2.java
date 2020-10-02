package chapter4;

import annotation.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ThreadSafe
public class Ex_4_15_ListHelper2<E> {

    public List<E> list = Collections.synchronizedList(new ArrayList<>());

    public boolean putIfAbsent(E x) {
        // 특정 클래스 내부에서 사용하는 락을 전혀 관계없는 제3의 클래스(도우미 클래스)에서 갖다 쓰기 때문에 위험하다.
        synchronized (list) {
            boolean absent = !list.contains(x);
            if (absent) {
                list.add(x);
            }
            return absent;
        }
    }
}
