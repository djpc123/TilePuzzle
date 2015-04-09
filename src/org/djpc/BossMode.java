package org.djpc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BossMode {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        for (String arg : args) {
            executorService.submit(() -> new PuzzleSolver(arg));
        }
        try {
            executorService.awaitTermination(2, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
