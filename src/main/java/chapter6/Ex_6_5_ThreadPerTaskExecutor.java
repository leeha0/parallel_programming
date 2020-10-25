package chapter6;

import java.util.concurrent.Executor;

public class Ex_6_5_ThreadPerTaskExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
