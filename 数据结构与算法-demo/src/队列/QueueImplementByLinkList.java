package 队列;
/*

Stack<T>()      创建一个空的队列
void Enqueue(T s)       往队列中添加一个新的元素
T Dequeue()     移除队列中最早添加的元素
boolean IsEmpty()       队列是否为空
int Size()      队列中元素的个数
 */
public class QueueImplementByLinkList<AnyType> {
    Node first;
    Node last;
    int size;
    public class Node{
        AnyType data;
        Node next;
        public Node(AnyType data,Node next){
            this.data=data;
            this.next=next;
        }
    }
    public void stack(){
        first=new Node(null,null);
        last=first;
        size=0;
    }
    public void enqueue(AnyType a){
        if(size==0){
            last.data=a;
            size++;
            return;
        }
        Node oldlast=last;
        last=new Node(a,null);
        oldlast.next=last;
        size++;
    }
    public AnyType dequeue(){
        if(size==0){
            System.out.print("队列为空");
            System.exit(0);
        }
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
