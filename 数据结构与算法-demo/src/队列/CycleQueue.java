package 队列;

public class CycleQueue {
    int[] arr;
    int start;//队首
    int end;//队尾
    int size=0;
    //初始化
    public void initqueue(int size){
        arr=new int[size];
        size=0;
        start=0;
        end=0;
    }

    //入队
    public void enqueue(int num){
        if(size>arr.length){
            System.out.println("队列已满");
            return;
        }
        if(end==arr.length){
            end=0;
        }
        arr[end++]=num;
        size++;
    }

    //出队
    public int dequeue(){
        if(size==0){
            System.out.println("队列为空");
            System.exit(0);
        }
        if(start==arr.length){
            start=0;
        }
        size--;
        return arr[start++];
    }

    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
}
