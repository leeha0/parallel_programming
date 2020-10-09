package chapter5;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import static java.util.Collections.*;

public class Ex_5_9_FileFinderApplication {

    private static final int BOUND = 10;
    private static final int N_CONSUMERS = 2;

    public static void startIndexing(File[] roots) {
        BlockingQueue<File> queue = new LinkedBlockingDeque<>(BOUND);
        FileFilter filter = file -> true;

        for (File root : roots) {
            new Thread(new Ex_5_8_FileCrawler(queue, filter, root)).start();
        }

        for (int i = 0; i < N_CONSUMERS; i++) {
            new Thread(new Ex_5_8_Indexer(queue)).start();
        }
    }

    public static void main(String[] args) {
//        Ex_5_9_FileFinderApplication.startIndexing();
    }
}
