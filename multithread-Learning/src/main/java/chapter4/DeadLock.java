package chapter4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ShareData{
    Lock a;
    Lock b;

    public ShareData(){
        a=new ReentrantLock();
        b=new ReentrantLock();
    }

    public void m1() throws InterruptedException {
        a.lock();
        System.out.println("持有A，尝试获取B");
        TimeUnit.SECONDS.sleep(1);
        b.lock();
        System.out.println("持有B，");
        a.unlock();
        b.unlock();
    }

    public void m2(){
        b.lock();
        System.out.println("持有B，尝试获取A");
        a.lock();
        System.out.println("持有A，");
        b.unlock();
        a.unlock();

    }


}
public class DeadLock {

    public static void main(String[] args) {

        ShareData shareData = new ShareData();
        new Thread(()->{
            try {
                shareData.m1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"A").start();

        new Thread(()->{
            shareData.m2();
        },"B").start();



    }
}
