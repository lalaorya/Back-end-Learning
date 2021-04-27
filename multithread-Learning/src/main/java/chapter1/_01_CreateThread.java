package chapter1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class _01_CreateThread {

    // 创建线程有三种方式
    // ① 继承Thread类
    public static class MyThread extends Thread{
        @Override
        public void run() {
            System.out.println("Thread1");
        }
    }

    // ② 实现Runnable接口
    // Runnable是一个函数式接口
    public static class MyThread2 implements Runnable{

        public void run() {
            System.out.println("Thread2");
        }
    }

    // ③ 实现callable接口
    public static class MyThread3 implements Callable<String>{

        public String call() throws Exception {
            return "Thread3";
        }
    }



    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        myThread.start();

        MyThread2 myThread2 = new MyThread2();
        new Thread(myThread2).start();

        FutureTask<String> myThread3 = new FutureTask<String>(new MyThread3());
        new Thread(myThread3).start();
        String result = null;
        try {
            result = myThread3.get();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}
