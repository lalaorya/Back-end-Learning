# JUC下的线程池原理

## 为什么要使用线程池

- 创建/销毁线程需要消耗系统资源，线程池可以**复用已创建的线程**。

- **控制并发的数量**。并发数量过多，可能会导致资源消耗过多，从而造成服务器崩溃。（主要原因）

- **可以对线程做统一管理**。

## JUC下线程池的体系结构

<img src="https://i.loli.net/2021/04/24/p6kNDfgGM75liUF.png" alt="image-20210424161015485" style="zoom:200%;" />

## 创建线程池的两种方法

1. 使用ThreadPoolExecutor的构造方法创建

   ```java
   public class ThreadPoolTest1 {
   
       public static void main(String[] args) {
   
           ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 8, 1, TimeUnit.SECONDS
                   , new ArrayBlockingQueue<>(5)
                   , Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
   
           for (int i = 0; i < 11; i++) {
               pool.execute(
                       () -> {                  System.out.println(Thread.currentThread().getName());
                       }
               );
   
           }
       }
   ```

   ![image-20210424162737796](https://i.loli.net/2021/04/24/duPVGTIw7tHyQUR.png)

2. 使用Executors这个工具类来实现

   JDK工具类为我们提供了四种常用的线程池，其实它们的底层源码都是调用ThreadPoolExecutor来实现的，传递的线程池参数不同罢了。

   [四种常见的线程池](https://redspider.gitbook.io/concurrent/di-san-pian-jdk-gong-ju-pian/12#123-si-zhong-chang-jian-de-xian-cheng-chi)

   工程中我们都是使用第一种方法来创建线程池，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的⻛险(OOM)

## 线程池ThreadPoolExecutor的七大参数

```java
 public ThreadPoolExecutor(int corePoolSize,
                           int maximumPoolSize,
                           long keepAliveTime,
                           TimeUnit unit,
                           BlockingQueue<Runnable> workQueue,
                           ThreadFactory threadFactory,
                           RejectedExecutionHandler handler) {
     if (corePoolSize < 0 ||
         maximumPoolSize <= 0 ||
         maximumPoolSize < corePoolSize ||
         keepAliveTime < 0)
         throw new IllegalArgumentException();
     if (workQueue == null || threadFactory == null || handler == null)
         throw new NullPointerException();
     this.acc = System.getSecurityManager() == null ?
             null :
             AccessController.getContext();
     this.corePoolSize = corePoolSize;
     this.maximumPoolSize = maximumPoolSize;
     this.workQueue = workQueue;
     this.keepAliveTime = unit.toNanos(keepAliveTime);
     this.threadFactory = threadFactory;
     this.handler = handler;
 }
```

- **corePoolSize**：核心线程的最大值，相当于正式员工，到点才下班
- **maximumPoolSize**：核心线程+非核心线程的最大值，非核心线程相当于临时工，核心线程处理不过来才会激活非核心线程，业务量低时先下班
- **keepAliveTime**：非核心线程闲置超时时长，超时了非核心线程被销毁
- **TimeUnit** **unit**：keepAliveTime的时间单位
- **workQueue**：阻塞队列，线程池的底层也用了阻塞队列，维护等待执行的线程对象
- **ThreadFactory** **threadFactory**：创建线程的工程，一般使用Excetory的默认实现默认实现
- **RejectedExecutionHandler** **handler**：阻塞队列满了之后对新来线程的拒绝策略，默认有四种
  1. ThreadPoolExecutor.AbortPolicy：**默认拒绝处理策略**，丢弃任务并抛出RejectedExecutionException异常。
  2. ThreadPoolExecutor.DiscardPolicy：丢弃新来的任务，但是不抛出异常。
  3. ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列头部（最旧的）的任务，然后重新尝试执行程序（如果再次失败，重复此过程）。
  4. ThreadPoolExecutor.CallerRunsPolicy：返回给上一步，由调用线程处理该任务。

## 线程池调度的策略

![image-20210424164526903](https://i.loli.net/2021/04/24/oIOtBiMUWTAXYZv.png)

线程池调度的核心是execute方法，总结完就是上图

```java
// JDK 1.8 
public void execute(Runnable command) {
    if (command == null)
        throw new NullPointerException();   
    int c = ctl.get();
    // 1.当前线程数小于corePoolSize,则调用addWorker创建核心线程执行任务
    if (workerCountOf(c) < corePoolSize) {
       if (addWorker(command, true))
           return;
       c = ctl.get();
    }
    // 2.如果不小于corePoolSize，则将任务添加到workQueue队列。
    if (isRunning(c) && workQueue.offer(command)) {
        int recheck = ctl.get();
        // 2.1 如果isRunning返回false(状态检查)，则remove这个任务，然后执行拒绝策略。
        if (! isRunning(recheck) && remove(command))
            reject(command);
            // 2.2 线程池处于running状态，但是没有线程，则创建线程
        else if (workerCountOf(recheck) == 0)
            addWorker(null, false);
    }
    // 3.如果放入workQueue失败，则创建非核心线程执行任务，
    // 如果这时创建非核心线程失败(当前线程总数不小于maximumPoolSize时)，就会执行拒绝策略。
    else if (!addWorker(command, false))
         reject(command);
}
```

这个对线程调度的源码没有深入分析，如addWork函数，拒绝策略是怎么实现的等。以后会再专门写一篇文章，可以参考《并发编程之美》的源码分析。

## 执行execute方法和submit方法有何区别？

1. execute()方法用于提交不需要返回值的任务，所以无法判断任务是否被线程池执行成功与否；
2. submit()方法用于提交需要返回值的任务。线程池会返回一个Future类型的对象，通过这个Future对象可以判断任务是否执行成功，并且可以通过Future的get()方法来获取返回值。

这也可以看到线程池的又一优点：灵活。

## 参考

- JavaGuide

- [深入浅出Java多线程-线程池原理](https://redspider.gitbook.io/concurrent/di-san-pian-jdk-gong-ju-pian/12#123-si-zhong-chang-jian-de-xian-cheng-chi)

- [Java线程池实现原理及其在美团业务中的实践](https://tech.meituan.com/2020/04/02/java-pooling-pratice-in-meituan.html)

  > 还没仔细研究，不过美团出品必属精品，值得参考

  





