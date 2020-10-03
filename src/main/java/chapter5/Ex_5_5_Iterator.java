package chapter5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ex_5_5_Iterator {

    private final List<Ex_5_5_Widget> widgetList = Collections.synchronizedList(new ArrayList<>());

    public void iterate() {
        // ConcurrentModificationException 발생할 수 있다. 결국 반복문 전체를 적절한 락으로 동기화 해야한다.
        for (Ex_5_5_Widget w : widgetList) {
            // doSomething
        }
    }
}
