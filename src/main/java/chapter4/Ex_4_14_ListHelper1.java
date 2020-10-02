package chapter4;

import annotation.NotThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NotThreadSafe
public class Ex_4_14_ListHelper1<E> {

    public List<E> list = Collections.synchronizedList(new ArrayList<>());

    // 아무 의미가 없는 락ㄷ을 대상으로 동기화되었다.
    // List 입장에서 단일 연산으로 동작하지 않는다.
    public synchronized boolean putIfAbsent(E x) {
        boolean absent = !list.contains(x);

        if (absent)
            list.add(x);
        return absent;
    }
}
