package 队列;

public class Test {
    public static void main(String[] args){
        //QueueImplementByArray qb=new QueueImplementByArray();
        /*
        qb.stack(3);
        qb.enqueue(4);
        qb.enqueue(66);
        qb.enqueue(4);
        qb.enqueue(53);
        qb.enqueue(53);
        qb.enqueue(53);
        qb.enqueue(53);
        qb.enqueue(53);
        qb.enqueue(53);
        qb.enqueue(53);
        qb.enqueue(53);
         */
        CycleQueue qb=new CycleQueue();
        qb.initqueue(5);
        qb.enqueue(3);
        qb.enqueue(2);
        qb.enqueue(6);

        System.out.println(qb.dequeue()+","+qb.dequeue()+","+qb.dequeue());

    }
}
