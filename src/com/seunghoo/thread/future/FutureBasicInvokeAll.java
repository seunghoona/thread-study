package com.seunghoo.thread.future;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class FutureBasicInvokeAll {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> hello = () -> {
            Thread.sleep(3000);
            return "Hello";
        };

        Callable<String> java = () -> {
            Thread.sleep(4000);
            return "java";
        };

        Callable<String> test = () -> {
            Thread.sleep(1100);
            return "test";
        };

        // 가장 오래걸리는 Callable이 끝나고 난후에야 리턴값이 존재하게 된다.
        // 여러 API에서 정보를 가져와야한다면 해당정보를 전부가져온 후 처리해야할 때 처리할 수 있다.
        List<Future<String>> futures = executorService.invokeAll(Arrays.asList(hello, java, test));

        futures.stream().forEach(s-> {
            try {
                System.out.println(s.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
}
