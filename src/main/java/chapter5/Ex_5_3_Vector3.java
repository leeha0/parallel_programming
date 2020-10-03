package chapter5;

import java.util.Vector;

public class Ex_5_3_Vector3 {

    public static void iterateVector(Vector vector) {
        // 반복문이 수행되는 동안 deleteLast 등과 같은 연산을 수행하지 않을 것이라는 보장이 없다.
        for (int i = 0; i < vector.size(); i++) {
            // doSomething(vector.get(i));
        }
    }
}
