package chapter5;

import net.jcip.annotations.GuardedBy;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Ex_5_6_HiddenIterator {
    @GuardedBy("this")
    private final Set<Integer> set = new HashSet<>();

    public synchronized void add(Integer i) {
        set.add(i);
    }

    public synchronized void remove(Integer i) {
        set.remove(i);
    }

    public void addThenThings() {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            add(r.nextInt());
        }
        // + 연산을 위해 내부적으로 Iterator를 이용해 각 요소의 toString을 호출하여 문자열로 변환한다.
        // 내부적으로 Iterator를 사용하기 때문에 ConcurrentModificationException이 발생할 수 있다.
        // HashSet 대신 이미 동기화된 synchronizedSet 메소드를 사용하면 문제를 해결할 수 있다.
        System.out.println("DEBUG: added ten elements to " + set);
    }
}
