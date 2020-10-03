package chapter5;

import java.util.Vector;

public class Ex_5_4_Vector4 {

    public static void iterateVector(Vector vector) {
        // 병렬 프로그램의 장점을 잃는다.
        synchronized (vector) {
            for (int i = 0; i < vector.size(); i++) {
                // doSomething(vector.get(i));
            }
        }
    }
}
