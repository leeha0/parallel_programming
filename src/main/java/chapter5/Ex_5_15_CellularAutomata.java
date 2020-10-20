package chapter5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Ex_5_15_CellularAutomata {
    private final Ex_5_15_Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public Ex_5_15_CellularAutomata(Ex_5_15_Board mainBoard) {
        this.mainBoard = mainBoard;
        int count = Runtime.getRuntime().availableProcessors();
        // CPU 만큼의 스레드를 생성하여 처리 분담
        this.barrier = new CyclicBarrier(count, mainBoard::commitNewValues);
        this.workers = new Worker[count];

        for (int i = 0; i < count; i++) {
            // 전체 면적을 CPU 만큼 분할
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
        }
    }

    public class Worker implements Runnable {
        private final Ex_5_15_Board board;

        public Worker(Ex_5_15_Board board) {
            this.board = board;
        }

        public void run() {
            while (!board.hasConverged()) {
                for (int x = 0; x < board.getMaxX(); x++) {
                    for (int y = 0; y < board.getMaxY(); y++) {
                        board.setNewValue(x, y, computeValue(x, y));
                    }
                }

                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException ex) {
                    return;
                }
            }
        }

        private Ex_5_15_Board computeValue(int x, int y) {
            return null;
        }

        public void start() {
            for (int i = 0; i < workers.length; i++) {
                new Thread(workers[i]).start();
            }
            mainBoard.waitForConvergence();
        }
    }
}
