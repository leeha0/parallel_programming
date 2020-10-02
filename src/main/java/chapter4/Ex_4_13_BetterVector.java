package chapter4;

import annotation.ThreadSafe;

import java.util.Vector;

@ThreadSafe
public class Ex_4_13_BetterVector<E> extends Vector<E> {

    // 동기화 대상이 두 개 이상의 클래스에 걸쳐 분산되었다.
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !contains(x);
        if (absent) {
            add(x);
        }
        return absent;
    }
}
