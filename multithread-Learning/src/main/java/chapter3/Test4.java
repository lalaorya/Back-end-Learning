package chapter3;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author virtual
 * @Date 2021/9/8 21:27
 * @Version 1.0
 */
public class Test4 {
    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(1);
        ShareData4 shareData4 = new ShareData4(atomicInteger,"11");
        new Thread(()->{
            while (true){
                synchronized (shareData4.s){
                    System.out.println("a"+shareData4.atomicInteger);
                    shareData4.inc();
                    try {
                        shareData4.s.wait(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }


        }).start();

        new Thread(()->{
            while (true){
                synchronized (shareData4.s){
                    System.out.println("b"+shareData4.atomicInteger);
                    shareData4.inc();
                    try {
                        shareData4.s.wait(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();


    }

}

class ShareData4{
    public AtomicInteger atomicInteger;
    public String s;

    public ShareData4(AtomicInteger atomicInteger,String lock) {
        this.atomicInteger = atomicInteger;
        this.s=lock;
    }

    public void inc(){
        atomicInteger.incrementAndGet();
    }
}
