package 栈;
// 栈的链表实现
/*
    Stack<T>()  创建一个空的栈
    void Push(T s）  往栈中添加一个新的元素
    T Pop() 移除并返回最近添加的元素
    boolean IsEmpty()   栈是否为空
    int Size()  栈中元素的个数
 */
public class StackImplementByLinklist<AnyType> {
    public Node<AnyType> first;
    int size;
    public class Node<AnyType>{
        AnyType data;
        Node<AnyType> next;
    }
    public void stack(){
        first=null;
        size=0;
    }
    public void push(AnyType a){
        Node oldNode=first;
        first=new Node();
        first.data=a;
        first.next=oldNode;
        size++;
    }
    public AnyType pop(){
        AnyType a=first.data;
        first=first.next;
        size--;
        return a;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
}
