package 栈;
/*
    Stack<T>()  创建一个空的栈
    void Push(T s）  往栈中添加一个新的元素
    T Pop() 移除并返回最近添加的元素
    boolean IsEmpty()   栈是否为空
    int Size()  栈中元素的个数
 */
public class StackImplementByArray<AnyType> {
    AnyType[] arr;
    int size;
    public StackImplementByArray(int capacity){
        stack(capacity);
    }
    public void stack(int capacity){
        arr=(AnyType[])new Object[capacity];
        size=0;

    }
    public void push(AnyType a){
        if(size==arr.length){
            changeArray(2*size+1);
        }
        arr[size]=a;
        size++;
    }
    public AnyType pop(){
        if(size==0){
            System.out.println("栈顶为空");
            System.exit(0);
        }
        AnyType a=arr[size-1];
        arr[size-1]=null;
        size--;
        return a;
    }

    public AnyType peek(){
        AnyType a=pop();
        push(a);
        return a;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
    public void changeArray(int newCapacity){
        AnyType[] newArr=(AnyType[])new Object[newCapacity];
        for(int i=0;i<arr.length;i++){
            newArr[i]=arr[i];
        }
        arr=newArr;
    }

}
