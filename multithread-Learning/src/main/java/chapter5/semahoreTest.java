package chapter5;

import java.util.concurrent.Semaphore;

class ShareData{
    Semaphore semaphore;
    public ShareData(){
        // 限制同时只能由三个线程同时执行
        this.semaphore=new Semaphore(3);
    }

    /**
     * 同时只能有三个线程执行
     */
    public void work(){
        try {
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName()+"正在执行，还剩"+semaphore.availablePermits()+"个资源,"+
                    semaphore.getQueueLength()+"个线程在等待");
            semaphore.release();
            System.out.println(Thread.currentThread().getName()+"释放了资源");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}


/**
 * semhore信号量 底层使用AQS实现
 * 具体作用是限制线程的数量，
 * 它有一个permit变量，
 *      调用acquire，premit-1
 *      调用release，premit+1
 *
 * 如果permit=0在调用acquire，线程就会被阻塞，直到premit>0
 * 这么看来，其实实现机制和LockSupport的park和unpark有点像，但是作用又和阻塞队列有点像
 */
public class semahoreTest {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                shareData.work();
            },i+"").start();

        }
    }




}
