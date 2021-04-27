package 队列;
/*

Stack<T>()      创建一个空的队列
void Enqueue(T s)       往队列中添加一个新的元素
T Dequeue()     移除队列中最早添加的元素
boolean IsEmpty()       队列是否为空
int Size()      队列中元素的个数
 */
public class QueueImplementByArray<AnyType> {
    AnyType[] arr;
    int first;
    int last;
    int size;
    public void stack(int capacity){
        arr=(AnyType[])new Object[capacity];
        first=0;
        last=0;
        size=0;
    }
    public void enqueue(AnyType a){
        if(size==arr.length){
            changeArray(2*size+1);
        }
        arr[last++]=a;
        size++;
    }
    public AnyType dequeue(){
        if(size==0){
            System.out.println("队列为空");
            System.exit(0);
        }
        AnyType a=arr[first++];
        arr[first-1]=null;
        size--;
        return a;
    }
    public void changeArray(int newCapacity){
        AnyType[] newArr=(AnyType[])new Object[newCapacity];
        for(int i=0;i<arr.length;i++){
            newArr[i]=arr[i];
        }
        arr=newArr;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }

}
