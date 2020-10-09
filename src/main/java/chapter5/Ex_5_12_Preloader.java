package chapter5;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Ex_5_12_Preloader {

    private final FutureTask<Ex_5_12_ProductInfo> future =
            new FutureTask<>(() -> loadProductInfo());

    private Ex_5_12_ProductInfo loadProductInfo() {
        return new Ex_5_12_ProductInfo();
    }

    private final Thread thread = new Thread(future);

    public void start() {
        thread.start();
    }

    public Ex_5_12_ProductInfo get() throws Ex_5_12_DataLoadException, InterruptedException {
        try {
            // 에외가 ExecutionException으로 한 번 감싼 다음 throw 된다.
            return future.get();
        } catch (ExecutionException e) {
            // CancellationException도 함께 잡아줘야 완벽하다.
            // Throwable로 받아와야 하기 때문에 실제 예외를 확인하기 어렵다.
            Throwable cause = e.getCause();

            if (cause instanceof Ex_5_12_DataLoadException) {
                // 자신이 처리할 수 있는 예외를 처리하고 그 외는 상위 메소드에게 throw 한다.
                throw (Ex_5_12_DataLoadException) cause;
            } else {
                throw launderThrowable(cause);
            }
        }
    }

    /**
     * 변수 t의 내용이 Error라면 그대로 throw한다. 변수 t의 내용이
     * RuntimeException이라면 그대로 리턴한다. 다른 모든 경우에는
     * IllegalStateException을 throw한다.
     */
    public static RuntimeException launderThrowable(Throwable t) {
        if (t instanceof RuntimeException) {
            // RuntimeException은 즉시 throw한다.
            return (RuntimeException) t;
        } else if (t instanceof Error) {
            // Error는 즉시 throw한다.
            throw (Error) t;
        } else {
            // 그 외의 에러는 처리 대상이 아니기 떄문에 IllegalStateException을 throw한다.
            throw new IllegalStateException("RuntimeException이 아님", t);
        }
    }
}
