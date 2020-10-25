package chapter6;

import java.util.concurrent.Executor;

public class Ex_6_6_WithinThreadExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
