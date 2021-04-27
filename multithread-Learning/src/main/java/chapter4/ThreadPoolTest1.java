package chapter4;

import java.sql.SQLOutput;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest1 {

    public static void main(String[] args) {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 8, 1, TimeUnit.SECONDS
                , new ArrayBlockingQueue<>(5)
                , Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());


        for (int i = 0; i < 11; i++) {
            pool.execute(
                    () -> {
                        System.out.println(Thread.currentThread().getName());
                    }
            );

        }


    }
}
