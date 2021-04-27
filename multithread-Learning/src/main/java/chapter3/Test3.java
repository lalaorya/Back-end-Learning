package chapter3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class ShareData3{
//    private volatile int flag;
    private AtomicInteger atomicInteger;
    private BlockingQueue queue;

    public ShareData3(BlockingQueue queue){
        this.queue=queue;
        this.atomicInteger=new AtomicInteger(0);
    }

    public void inc() throws InterruptedException {

        while (true){
            int i = atomicInteger.incrementAndGet();
            queue.put(i);
            System.out.println(Thread.currentThread().getName()+":"+i);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    public void dec() throws InterruptedException {


        while (true){
            Object take = queue.take();
            System.out.println(Thread.currentThread().getName()+":"+take);
        }
    }




}


public class Test3 {
    public static void main(String[] args) {
        System.out.println(Runtime.getRuntime().availableProcessors());

//        ShareData3 data1=new ShareData3(new ArrayBlockingQueue(10));
        ShareData3 data1 = new ShareData3(new SynchronousQueue());
        new Thread(() -> {
                try {
                    data1.inc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        },"gene").start();

        new Thread(() -> {
            try {
                data1.dec();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"custom").start();
    }
}
