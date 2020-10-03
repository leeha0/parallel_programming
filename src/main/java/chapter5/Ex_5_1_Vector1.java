package chapter5;

import java.util.Vector;

public class Ex_5_1_Vector1 {

    // 여러 스레드가 동시에 getLast, deleteLast를 사용할 때 ArrayIndexOutOfBoundsException 등의 문제가 발생할 수 있다.
    // Vector의 입장에서 스레드 안정성이 보장된다.
    public static Object getLast(Vector list) {
        int lastIndex = list.size() - 1;
        return list.get(lastIndex);
    }

    public static void deleteLast(Vector list) {
        int lastIndex = list.size() - 1;
        list.remove(lastIndex);
    }
}
