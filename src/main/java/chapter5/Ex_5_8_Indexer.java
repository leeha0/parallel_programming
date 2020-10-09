package chapter5;

import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.concurrent.BlockingQueue;

@RequiredArgsConstructor
public class Ex_5_8_Indexer implements Runnable {

    private final BlockingQueue<File> queue;

    @Override
    public void run() {
        try {
            while (true) {
                indexFile(queue.take());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void indexFile(File take) {
        // 파일 인덱싱
    }
}
