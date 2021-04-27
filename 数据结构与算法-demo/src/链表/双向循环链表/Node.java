package 链表.双向循环链表;

public class Node<Anytype> {
    public Anytype data;//数据
    public Node<Anytype> prev;//前一个节点
    public Node<Anytype> next;//后一个节点
    public Node(Anytype data,Node<Anytype> prev,Node<Anytype> next){
        this.data=data;
        this.prev=prev;
        this.next=next;
    }
}
