package com.shang.javatest.Account.method3;

/**
 * @author baj
 * @creat 2022-05-15 9:20
 */
/**
 * @author zzx
 * @desc 生产者与消费者
 *
 */
public class Concurrent {
    //常量
    private static int MAX_VALUE = 100;

    public static void main(String[] args) {
        Concurrentcomm con = new Concurrentcomm();
        //生产者
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    for (int i = 0; i < MAX_VALUE; i++) {
                        Thread.sleep(0);
                        con.product();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
        // 消费者
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                    for (int i = 0; i < MAX_VALUE; i++) {
                        con.customer();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}