package com.seunghoo.thread;

public class ThreadJoinMethod {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "시작");
        try {

            Thread thread = new Thread(() -> {
                System.out.println("Thread.currentThread().getName() = " + Thread.currentThread().getName());
                secondCallFunction();
            });
            thread.start();
            thread.join();
        } catch (Exception e) {
          e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "종료");
    }

    private static void secondCallFunction() {
        System.out.println("두번째 호출입니다.");
        thirdCallFunction();
    }

    private static void thirdCallFunction() {
        System.out.println("세번째 호출입니다.");
    }
}
