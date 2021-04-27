package chapter1;

public class _02_TreandGroup {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getThreadGroup().getName());
            System.out.println(Thread.currentThread().getName());
        });

        // 设置线程的优先级
        thread.setPriority(10);
        System.out.println(thread.getPriority());

        // Thread的常用静态方法
        // yield 放弃当前正在执行的线程的时间片，线程进入就绪状态
        // sleep 是当前线程睡眠一段时间

        // 自定义线程Thread类的常用方法
        // start 开始执行此线程
        // join 等待当前进行执行完毕

        // 线程组的常见方法
        // thread.getThreadGroup().
        // 新建一个线程组
        ThreadGroup threadGroup = new ThreadGroup("groud1");


        Thread.sleep(111111);
        thread.start();


    }
}
