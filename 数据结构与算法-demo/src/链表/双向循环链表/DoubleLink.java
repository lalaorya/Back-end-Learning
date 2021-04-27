package 链表.双向循环链表;

public class DoubleLink<AnyType> {
    Node<AnyType> head;//头指针
    Node<AnyType> end;//尾节点
    int size;//记录链表长度

    //初始化链表
    public void initlist(){
        end=new Node<>(null,null,null);
        head=new Node<>(null,null,end);
        end.prev=head;
        end.next=head;
        size=0;
    }

    //获取长度
    public int length(){
        return size;
    }

    //获取节点
    public Node<AnyType> getNode(int index){
        Node<AnyType> n;
        if(index>=size/2){
            n=end;
            for(int i=length();i>index;i--){
                n=n.prev;
            }
            return n;
        }
        else{
            n=head;
            for(int i=0;i<=index;i++){
                n=n.next;
            }
            return n;
        }
    }

    //添加元素
    public void add(AnyType a){
        Node<AnyType> renode=new Node<>(a,getNode(size-1),end);
        renode.prev.next=renode;
        renode.next.prev=renode;
        size++;
    }

    //插入元素
    public void insert(int i,AnyType a){
        Node<AnyType> n=getNode(i);
        Node<AnyType> renode=new Node<>(a,n.prev,n);
        n.prev.next=renode;
        n.prev=renode;
        size++;
    }

    //删除元素
    public AnyType remove(int i){
        Node<AnyType> n=getNode(i);
        AnyType data=n.data;
        n.prev.next=n.next;
        n.next.prev=n.prev;
        size--;
        return data;
    }

    //获取i位置的数据
    public AnyType get(int i){
        return getNode(i).data;
    }

    //为i位置元素重新赋值
    public AnyType set(int i,AnyType a){
        Node<AnyType> n=getNode(i);
        AnyType old=n.data;
        n.data=a;
        return old;

    }

    //清空链表
    public void clear(){
        initlist();
    }



    public void print(){
        for(int i=0;i<size;i++){
            System.out.println(getNode(i).data);
        }
    }
}
