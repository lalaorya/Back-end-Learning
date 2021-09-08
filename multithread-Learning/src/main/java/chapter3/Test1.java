package chapter3;


import java.util.concurrent.locks.LockSupport;

/**
 * 生产者消费之模型的synchronized实现
 * +a -a +a -a
 */
class shareData{
    private int flag=0;

    /**
     * 生产者
     * @throws InterruptedException
     */
    public void inc() throws InterruptedException {
        synchronized (this){
            while(flag!=0){
                this.wait();
            }
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName()+":A");
            flag=1;
            this.notifyAll();
        }
    }

    /**
     * 消费者
     * @throws InterruptedException
     */
    public void dec() throws InterruptedException {
        synchronized (this){
            while(flag==0){
                this.wait();
            }
            System.out.println(Thread.currentThread().getName()+":A");

            flag=0;
            this.notifyAll();
        }
    }

}
public class Test1 {

    public static void main(String[] args) {
        shareData shareData = new shareData();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {

                    shareData.inc();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"gene").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareData.dec();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"custom").start();


    }










}

