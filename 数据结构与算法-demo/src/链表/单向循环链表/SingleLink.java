package 链表.单向循环链表;

public class SingleLink<AnyType> {


    //首元节点
    public Node<AnyType> first;

    //头指针
    public Node<AnyType> head;

    //链表长度
    int thesize;

    //初始化链表
    public boolean initlist(){
        thesize=0;
        first=new Node<>(null,null);
        head=new Node<>(null,first);
        first.next=head;
        return true;
    }

    //判断链表是否为空
    public boolean isEmpty(){
        return thesize==0;
    }

    //获取节点
    public Node<AnyType> getNode(int i){
        Node<AnyType> renode=head;
        for(int j=-2;j<i;j++){
            renode=renode.next;
        }
        return renode;
    }

    //在末尾添加元素
    public void add(AnyType a){
        Node<AnyType> renode=new Node<>(a,null);
        getNode(thesize-1).next=renode;
        renode.next=first.next;
        thesize++;
    }

    //删除i位置节点，并返回删掉的数据
    public AnyType remove(int i){
        if(i==thesize-1){
            AnyType a=getNode(thesize-1).data;
            getNode(thesize-2).next=first.next;
            return a;
        }
        Node<AnyType> prev=getNode(i-1);
        AnyType a=prev.next.data;
        prev.next=prev.next.next;
        thesize--;
        return  a;
    }

    public void remove2(Node<AnyType> n){

    }

    //在i位置插入新节点
    public void insert(int i,AnyType a){
        Node<AnyType> prev=getNode(i-1);
        Node<AnyType> renode=new Node<>(a,prev.next);
        prev.next=renode;
        thesize++;
    }

    //获取i位置节点的数据
    public AnyType get(int i){
        return getNode(i).data;
    }

    //为i位置元素重新赋值
    public void set(int i,AnyType a){
        getNode(i).data=a;
    }

    //返回链表节点个数
    public int length(){
        return thesize;
    }

    //清空链表
    public void clear(){
        initlist();
    }

    //打印链表
    public void print(){
        for(int i=0;i<thesize;i++){
            System.out.println(getNode(i).data);
        }
    }

}
