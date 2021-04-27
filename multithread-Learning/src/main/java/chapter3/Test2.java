package chapter3;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Lock和condition实现生产者消费者模型
 */

class ShareData2{
    private int flag;
    private Lock lock;
    private Condition gene;
    private Condition custom;
    public ShareData2(){
        lock=new ReentrantLock();
        gene=lock.newCondition();
        custom=lock.newCondition();
    }

    public void inc() throws InterruptedException {
        lock.lock();
        if(flag!=0){
            gene.await();
        }
        System.out.println(Thread.currentThread().getName()+":A");
        flag=1;
        custom.signalAll();
        lock.unlock();
    }

    public void dec() throws InterruptedException {
        lock.lock();
        if(flag==0){
            custom.await();
        }
        System.out.println(Thread.currentThread().getName()+":A");
        flag=0;
        gene.signalAll();
        lock.unlock();
    }
}


public class Test2 {

    public static void main(String[] args) {
        ShareData2 data1=new ShareData2();
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    data1.inc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"gene").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    data1.dec();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"custom").start();
    }




}
