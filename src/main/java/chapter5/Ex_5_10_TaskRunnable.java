package chapter5;

import java.util.concurrent.BlockingQueue;

public class Ex_5_10_TaskRunnable<T> implements Runnable {

    BlockingQueue<T> queue;

    @Override
    public void run() {
        try {
            processTask(queue.take());
        } catch (InterruptedException e) {
            // 인터럽트가 발생한 사실을 저장한다.
            // 상위 호출 메소드가 인터럽트 상황이 발생했을을 알 수 있도록 한다.
            Thread.currentThread().interrupt();
        }
    }

    private void processTask(T take) {
        // 작업 처리
    }
}
