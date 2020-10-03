package chapter4;

import annotation.ThreadSafe;
import net.jcip.annotations.GuardedBy;

import java.util.HashSet;
import java.util.Set;

@ThreadSafe
public class Ex_4_2_PersonSet {
    // HashSet은 스레드에 안전한 객체가 아니지만, 한정 기법을 사용하여 스레드 안전하게 구현 (모든 상태 정보를 락으로 통제)
    @GuardedBy("this")
    private final Set<Ex_4_2_Person> mySet = new HashSet<Ex_4_2_Person>(); // private 선언

    // synchronized를 통한 PersonSet 객체 락 (암묵적인 락)
    public synchronized void addPerson(Ex_4_2_Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Ex_4_2_Person p) {
        return mySet.contains(p);
    }
}