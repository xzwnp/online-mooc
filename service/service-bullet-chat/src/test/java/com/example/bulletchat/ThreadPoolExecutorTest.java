package com.example.bulletchat;

import java.util.Scanner;
import java.util.concurrent.*;

/**
 * com.example.bulletchat
 *
 * @author xiaozhiwei
 * 2023/3/30
 * 17:47
 */
public class ThreadPoolExecutorTest {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Scanner reader = new Scanner(System.in);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 5, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        Callable<Double> task = () -> {
            System.out.println(Thread.currentThread().getName() + "随便做点什么");
            return Math.random();
        };

        while (true) {
            System.out.println("是否发布任务?");
            reader.next();
            Future<Double> future = executor.submit(task);
            System.out.println("执行结果:" + future.get());
        }

    }
}
