package com.complexible.stardog.main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jamesanto on 5/19/15.
 */
public class App {
    public static void main(String[] args) {
        if(args.length < 3) {
            System.out.println("Usage: java -jar stardog.jar <config_file_path> <thread_pool_size> <num_entities>");
            System.exit(0);
        }
        final EmpireConnection stardogConnection = EmpireConnection.create(args[0]);
        int poolSize = Integer.parseInt(args[1]);
        int numEntities = Integer.parseInt(args[2]);
        AtomicInteger atomicInteger = test(stardogConnection, poolSize, numEntities, "default");
        try {
            Thread.sleep(numEntities * 3 * 1000); //Let's sleep for n x 3 seconds to check if any entity is persisted
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Completed all");
    }

    private static AtomicInteger test(final EmpireConnection empireConnection, int poolSize, final int numEntities, final String tag) {
        final ExecutorService executor = Executors.newFixedThreadPool(poolSize);

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                System.out.println("Shutting down executor forcefully");
                executor.shutdownNow();
                System.out.println("Shut down executor forcefully");
            }
        });

        final AtomicInteger completedCount = new AtomicInteger(0);
        for (int i = 0; i < numEntities; i++) {
            final int t = i + 1;
            executor.submit(new Runnable() {
                                @Override
                                public void run() {
                                    Book aNewBook = BookFixture.create();
                                    empireConnection.saveObject(aNewBook);
                                    final int cc = completedCount.incrementAndGet();
                                    System.out.println("["+tag+"]Saved object[" + aNewBook.getRdfId() + "] " + t + " " +cc + "/" + numEntities);
                                    if(cc >= numEntities) {
                                        executor.shutdown();
                                        System.out.println("Shutting down executor");
                                    }
                                }
                            }
            );

        }
        return completedCount;
    }
}
