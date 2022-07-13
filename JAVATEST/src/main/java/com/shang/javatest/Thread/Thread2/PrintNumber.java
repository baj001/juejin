package com.shang.javatest.Thread.Thread2;

/**
 * @author baj
 * @creat 2022-05-17 21:12
 */
public class PrintNumber {
    private volatile int number = 1;
    private volatile int value = 1;

    void firstPrint() {
        synchronized (this) {
            //只有等于1的时候才会跳过如下的wait，去执行打印任务
            while (value != 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ": " + number);
            number++;
            value = 2;
            notifyAll();
        }
    }

    void secondPrint() {
        synchronized (this) {
            while (value != 2) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ": " + number);
            number++;
            value = 3;
            notifyAll();
        }
    }

    void thirdPrint() {
        synchronized (this) {
            while (value != 3) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ": " + number);
            number++;
            value = 1;
            notifyAll();
        }
    }
}

class Main {
    public static void main(String[] args) {
        PrintNumber printNumber = new PrintNumber();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //此线程会多打印一次
                for (int i = 0; i < 34; i++) {
                    printNumber.firstPrint();
                }
            }
        }, "线程一").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 33; i++) {
                    printNumber.secondPrint();
                }
            }
        }, "线程二").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 33; i++) {
                    printNumber.thirdPrint();
                }
            }
        }, "线程三").start();
    }
}
