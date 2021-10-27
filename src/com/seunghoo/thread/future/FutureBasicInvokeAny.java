package com.seunghoo.thread.future;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FutureBasicInvokeAny {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

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

        String s = executorService.invokeAny(Arrays.asList(hello, java, test));
        System.out.println("s = " + s);
    }
}
