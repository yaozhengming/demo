package com.example.demo.lock;

/**
 * @author yaozhengming
 * @date 2019.2.28
 */
public class LockDemo {
    volatile int i = 0;

    public void add() {
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
        LockDemo ld = new LockDemo();

        //期望20000
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ld.add();
                }
            }).start();
        }
        Thread.sleep(2000L);
        System.out.println(ld.i);
    }
}
