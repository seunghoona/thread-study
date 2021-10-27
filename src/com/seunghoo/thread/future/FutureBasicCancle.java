package com.seunghoo.thread.future;

import java.util.concurrent.*;

public class FutureBasicCancle {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<String> hello = () -> {
            Thread.sleep(1000);
            return "strHello";
        };
        Future<String> submit = executorService.submit(hello);
        boolean done = submit.isDone(); //작업종료 여부
        System.out.println("done = " + done);
        submit.cancel(true);
        System.out.println("futureCallableResponseVal = " + submit.get());
        System.out.println("submit.isDone() = " + submit.isDone());
    }
}
